package com.csosa.fntest.activities

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.csosa.fntest.R
import com.csosa.fntest.base.BaseActivity
import com.csosa.fntest.commons.*
import com.csosa.fntest.databinding.ActivityMapsBinding
import com.csosa.fntest.domain.model.FleetType
import com.csosa.fntest.idlingresource.EspressoIdlingResource
import com.csosa.fntest.models.VehiclePresentation
import com.csosa.fntest.models.states.MapsViewState
import com.csosa.fntest.viewmodel.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel


internal class MapsActivity :
    BaseActivity(),
    GoogleMap.OnCameraIdleListener,
    GoogleMap.OnMarkerClickListener,
    OnMapReadyCallback {

    // region Members

    private val mapsViewModel by viewModel<MapsViewModel>()
    private val tag = MapsActivity::class.java.name
    private var markers = mutableListOf<Marker>()

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    // endregion

    // region Android API

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        observeVehiclesViewState()
        binding.fab.setOnClickListener {
            goBack()
        }
    }

    // endregion

    // region Google Maps Listeners

    override fun onMapReady(googleMap: GoogleMap) {


        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.map_style
                )
            )
            if (!success) {
                Log.e(tag, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(tag, "Can't find style. Error: ", e)
        }

        map = googleMap
        with(googleMap) {
            setOnCameraIdleListener(this@MapsActivity)
            setOnMarkerClickListener(this@MapsActivity)

            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true

            val vehiclePresentation =
                intent.getParcelableExtra<VehiclePresentation>(NavigationUtils.VEHICLE_PARCEL_KEY)

            if (vehiclePresentation != null) {

                mapsViewModel.updateSelected(vehiclePresentation)

                val latLng = LatLng(
                    vehiclePresentation.coordinate.latitude,
                    vehiclePresentation.coordinate.longitude
                )
                moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            } else {

                val latLng = LatLng(
                    SampleData.bounds.center.latitude,
                    SampleData.bounds.center.longitude
                )
                moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            }
        }
    }

    override fun onCameraIdle() {

        Log.d(tag, "onCameraIdle")
        val apiKey = resources.getString(R.string.google_maps_key)

        val bounds: LatLngBounds = map.projection.visibleRegion.latLngBounds

        Log.d(tag, "p1.lat" + bounds.northeast.latitude)
        Log.d(tag, "p1.long" + bounds.northeast.longitude)
        Log.d(tag, "p1.lat" + bounds.southwest.latitude)
        Log.d(tag, "p1.long" + bounds.southwest.longitude)

        mapsViewModel.executeGetVehiclesByBounds(
            apiKey,
            bounds.toBounds(),
            bounds.toBounds().center
        )


    }

    override fun onMarkerClick(marker: Marker): Boolean {

        val vehiclePresentation = marker.tag as VehiclePresentation?
        vehiclePresentation?.let {

            mapsViewModel.updateSelected(it)
        }
        return false
    }

    // endregion

    // region Private API

    private fun observeVehiclesViewState() {
        mapsViewModel.mapsViewState.observe(this, Observer { state ->

            handleVehiclesLoading(state)

                handleSelected(state.selected)

            state.vehicles?.let { vehicles ->
                if (vehicles.isNotEmpty()) {
                    handleVehicles(vehicles, state.selected)
                }
            }

            handleVehiclesError(state)
        })
    }

    private fun handleSelected(vehiclePresentation: VehiclePresentation?) {

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        if(vehiclePresentation!= null) {

            binding.vehicle = vehiclePresentation
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

    }

    private fun handleVehiclesLoading(state: MapsViewState) {

        if (state.isLoading) {
            binding.loading.show()
        } else {
            binding.loading.hide()
        }
    }

    private fun handleVehicles(vehicles: List<VehiclePresentation>, selected: VehiclePresentation?) {

        markers.forEach {
            it.remove()
        }

        vehicles.forEach { vehicle ->

            val vehicleIcon = when (vehicle.fleetType) {
                FleetType.TAXI.name -> generateSmallIcon(R.drawable.taxi_icon)
                FleetType.POOLING.name -> generateSmallIcon(R.drawable.pool_icon)
                else -> generateSmallIcon(R.drawable.unknown_icon)
            }

            val vehicleLocation = LatLng(
                vehicle.coordinate.latitude,
                vehicle.coordinate.longitude
            )


            val vehicleMarker = map.addMarker(
                MarkerOptions()
                    .position(vehicleLocation)
                    .rotation(vehicle.heading)
                    .icon(BitmapDescriptorFactory.fromBitmap(vehicleIcon))
            )

            vehicleMarker.tag = vehicle
            markers.add(vehicleMarker)
        }

        selected?.let {

            val latlng = LatLng(it.coordinate.latitude, it.coordinate.longitude)
            val vehicleMarker = map.addMarker(
                MarkerOptions()
                    .position(latlng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_selected))
            )
            markers.add(vehicleMarker)
        }


    }

    private fun generateSmallIcon(@DrawableRes id: Int): Bitmap {

        val height = 96
        val width = 96
        val bitmap = BitmapFactory.decodeResource(baseContext.resources, id)
        return Bitmap.createScaledBitmap(bitmap, width, height, false)
    }

    private fun handleVehiclesError(state: MapsViewState) {
        EspressoIdlingResource.decrement()
         state.error?.run {
             showSnackBar(
                 binding.bottomSheet,
                 getString(this.message),
                 isError = true
             )
         }
    }

    fun goBack() {

        finish()
    }

    // endregion

}

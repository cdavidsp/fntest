package com.csosa.fntest.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.csosa.fntest.R
import com.csosa.fntest.adapters.createVehiclesAdapter
import com.csosa.fntest.base.BaseActivity
import com.csosa.fntest.commons.NavigationUtils
import com.csosa.fntest.commons.hide
import com.csosa.fntest.commons.show
import com.csosa.fntest.commons.showSnackBar
import com.csosa.fntest.commons.startActivity
import com.csosa.fntest.databinding.ActivityVehiclesBinding
import com.csosa.fntest.idlingresource.EspressoIdlingResource
import com.csosa.fntest.models.VehiclePresentation
import com.csosa.fntest.models.states.ListVehiclesViewState
import com.csosa.fntest.viewmodel.ListVehiclesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ListVehiclesActivity : BaseActivity() {

    // region Members

    private val listVehiclesViewModel by viewModel<ListVehiclesViewModel>()

    private lateinit var binding: ActivityVehiclesBinding

    private val vehiclesAdapter = createVehiclesAdapter {

        startActivity<MapsActivity> {
            putExtra(NavigationUtils.VEHICLE_PARCEL_KEY, it)
        }
        //listVehiclesViewModel.cleanVehicles()
    }

    // endregion

    // region Android API

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vehicles)
        binding.vehiclesRecyclerView.itemAnimator = null
        configSupportActionBar()
        observeVehiclesViewState()
        binding.fab.setOnClickListener {

            startActivity<MapsActivity> ()
        }
    }

    override fun onResume() {

        super.onResume()
        obtainVehicles()
    }

    private fun obtainVehicles(){

        binding.vehiclesRecyclerView.hide()
       // listVehiclesViewModel.cleanVehicles()
        val apiKey = resources.getString(R.string.google_maps_key)
        listVehiclesViewModel.executeGetAllVehicles(apiKey)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return if (item.itemId == R.id.action_settings) {
            startActivity<AboutActivity>()
            true
        } else super.onOptionsItemSelected(item)
    }

    // endregion

    // region Private API

    private fun configSupportActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.title_list_vehicle)
    }

    private fun observeVehiclesViewState() {
        listVehiclesViewModel.vehiclesViewState.observe(this, Observer { state ->


            if (!state.isLoading) {

                state.vehicles?.let { vehicles ->
                    if (vehicles.isNotEmpty()) {
                        handleVehicles(vehicles)
                    } else {
                        binding.noVehiclesTextView.show()
                    }
                }

            }

            handleVehiclesLoading(state)

            handleVehiclesError(state)
        })
    }

    private fun handleVehiclesLoading(state: ListVehiclesViewState) {

        if (state.isLoading) {

            binding.loading.show()
            binding.vehiclesRecyclerView.hide()
        } else {

            binding.loading.hide()
            binding.vehiclesRecyclerView.show()
        }
    }

    private fun handleVehicles(vehicles: List<VehiclePresentation>) {

        binding.noVehiclesTextView.hide()
        binding.vehiclesRecyclerView.apply {

            adapter = vehiclesAdapter.apply {

                submitList(vehicles)
                EspressoIdlingResource.decrement()
            }
        }
    }

    private fun handleVehiclesError(state: ListVehiclesViewState) {

        EspressoIdlingResource.decrement()
        state.error?.run {

            showSnackBar(
                binding.vehiclesRecyclerView,
                getString(this.message),
                isError = true
            )
        }
    }

    // endregion

}

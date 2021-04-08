package com.csosa.fntest.data.respository

import androidx.core.os.ConfigurationCompat
import com.csosa.fntest.data.local.dao.VehiclesDao
import com.csosa.fntest.data.local.dao.VehiclesQueriesDao
import com.csosa.fntest.data.local.mappers.toDomain
import com.csosa.fntest.data.local.mappers.toEntity
import com.csosa.fntest.data.local.models.VehicleQueryEntity
import com.csosa.fntest.data.preferences.AppPreferences
import com.csosa.fntest.data.preferences.IntervalPolicyOfRemoteData
import com.csosa.fntest.data.preferences.PolicyOfRemoteData
import com.csosa.fntest.data.remote.api.FNTestApiService
import com.csosa.fntest.data.remote.api.GoogleMapsApiService
import com.csosa.fntest.data.remote.mappers.toDomain
import com.csosa.fntest.domain.model.Bounds
import com.csosa.fntest.domain.model.Coordinate
import com.csosa.fntest.domain.model.Vehicle
import com.csosa.fntest.domain.respository.IVehiclesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.internal.lockAndWaitNanos
import java.util.*

class VehiclesRepository(
    private val fNTestApiService: FNTestApiService,
    private val googleMapsApiService: GoogleMapsApiService,
    private val appPreferences: AppPreferences,
    private val vehiclesDao: VehiclesDao,
    private val vehiclesQueriesDao: VehiclesQueriesDao
) : IVehiclesRepository {


    override suspend fun getVehiclesByBounds(apiKey: String, location: Coordinate, bounds: Bounds): Flow<List<Vehicle>> = flow {


        val mustBeCallToBE = PolicyOfRemoteData.mustBeCallRemote(
                appPreferences.lastCallVehicles, IntervalPolicyOfRemoteData.EACH_FIVE_MINUTES
        )

        val queries = vehiclesQueriesDao.getAllInside(bounds.p1.latitude, bounds.p1.longitude, bounds.p2.latitude, bounds.p2.longitude)

        if (mustBeCallToBE || queries.isEmpty()) {

            vehiclesQueriesDao.deleteAll()
            vehiclesDao.deleteAll()

            vehiclesQueriesDao.insert(
                    VehicleQueryEntity(
                            1,
                            location.latitude,
                            location.longitude,
                            bounds.p1.latitude,
                            bounds.p1.longitude,
                            bounds.p2.latitude,
                            bounds.p2.longitude
                    ))

            val vehiclesResponse = fNTestApiService.getVehiclesByBounds(bounds.p1.latitude, bounds.p1.longitude, bounds.p2.latitude, bounds.p2.longitude)


            val locale = Locale.UK

            val origins = String.format(locale,"%f,%f", location.latitude, location.longitude)

            val maxDestinations = 20

            var numberOfCalls = vehiclesResponse.poiList.size / maxDestinations

            numberOfCalls = if (vehiclesResponse.poiList.size % maxDestinations == 0) numberOfCalls else numberOfCalls + 1

            for (call in 0 until numberOfCalls) {

                val toIndex = if ((call + 1) * maxDestinations > vehiclesResponse.poiList.size) vehiclesResponse.poiList.size - 1 else (call + 1) * maxDestinations - 1
                val initialList = vehiclesResponse.poiList.subList(call * maxDestinations, toIndex + 1)
                var destinations = ""
                val vehiclesCall = mutableListOf<Vehicle>()


                for (vehicleResponse in initialList) {

                    vehiclesCall.add(vehicleResponse.toDomain())

                    destinations = destinations + "|" + String.format(locale,"%f,%f", vehicleResponse.coordinate.latitude, vehicleResponse.coordinate.longitude)
                }

                delay(50)
                val distanceMatrix = googleMapsApiService.getDistanceMatrix(origins, destinations, apiKey, locale.isO3Country)


                if (distanceMatrix.rows.isNotEmpty()) {

                    for ((index, value) in distanceMatrix.rows[0].elements.withIndex()) {

                        if (value.status == "OK") {

                            vehiclesCall[index].distance = value.distance.text
                            vehiclesCall[index].distanceValue = value.distance.value
                            vehiclesCall[index].duration = value.duration.text
                            vehiclesCall[index].durationValue = value.duration.value
                        }

                    }

                }

                vehiclesDao.insertAll(vehiclesCall.map { t -> t.toEntity() })

            }


            appPreferences.lastCallVehicles = Date().time
        }

        val vehiclesEntities = vehiclesDao.getAllByBounds(bounds.p1.latitude, bounds.p1.longitude, bounds.p2.latitude, bounds.p2.longitude)

        emit(vehiclesEntities.map { t -> t.toDomain() })
    }
    // }.flowOn(Dispatchers.IO)

}

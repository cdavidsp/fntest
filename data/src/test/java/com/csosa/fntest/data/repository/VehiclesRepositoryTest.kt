package com.csosa.fntest.data.repository

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.csosa.fntest.data.BaseTest
import com.csosa.fntest.data.respository.VehiclesRepository
import com.csosa.fntest.domain.model.Bounds
import com.csosa.fntest.domain.model.Coordinate
import com.csosa.fntest.domain.respository.IVehiclesRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
internal class VehiclesRepositoryTest : BaseTest() {

    private lateinit var vehiclesRepository: IVehiclesRepository

    private val bounds = Bounds(Coordinate(53.694865, 9.757589), Coordinate(53.394655, 10.099891))

    @Before
    override fun setup() {
        super.setup()

        vehiclesRepository = VehiclesRepository(fnTestApiService, googleMapsApiService, appPreferences, vehiclesDao, vehiclesQueriesDao)
    }

    @Test
    fun `when all vehicles are requested at the first time, then return all from api `() {
        runBlocking(Dispatchers.IO) {

            val vehiclesFlow = vehiclesRepository.getVehiclesbyBounds("", bounds.center, bounds)
            val numberOfVehicles = 8 // in my_taxi.json

            vehiclesFlow.collect { vehicles ->
                Truth.assertThat(vehicles.size).isEqualTo(numberOfVehicles)
            }
        }
    }

    @Test
    fun `when all vehicles are requested at the second time, then return all from db `() {
        runBlocking(Dispatchers.IO) {

            val vehiclesFlow = vehiclesRepository.getVehiclesbyBounds("", bounds.center, bounds)
            val numberOfVehicles = 8 // in post.json
            var firstVehicleId: Long? = null

            //First time
            vehiclesFlow.collect { vehicles ->
                firstVehicleId = vehicles.firstOrNull()?.id
            }

            if (firstVehicleId != null) {
                //only for testing
                vehiclesDao.deleteById(firstVehicleId!!)
            }
            //Second time
            vehiclesFlow.collect { vehicles ->
                Truth.assertThat(vehicles.size).isEqualTo(numberOfVehicles - 1)
            }
        }
    }
}

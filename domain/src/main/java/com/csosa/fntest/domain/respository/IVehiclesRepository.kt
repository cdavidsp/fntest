package com.csosa.fntest.domain.respository

import com.csosa.fntest.domain.model.Bounds
import com.csosa.fntest.domain.model.Coordinate
import com.csosa.fntest.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface IVehiclesRepository {

    suspend fun getVehiclesByBounds(apiKey: String, location: Coordinate, bounds: Bounds): Flow<List<Vehicle>>
}

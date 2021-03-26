package com.csosa.fntest.data.remote.api

import com.csosa.fntest.data.remote.models.VehiclesByBoundsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FNTestApiService {

    @GET("/")
    suspend fun getVehiclesByBounds(
        @Query(value="p1Lat") p1Lat: Double,
        @Query(value="p1Lon") p1Lon: Double,
        @Query(value="p2Lat") p2Lat: Double,
        @Query(value="p2Lon") p2Lon: Double
    ): VehiclesByBoundsResponse

}

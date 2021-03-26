package com.csosa.fntest.data.remote.api

import com.csosa.fntest.data.remote.models.GoogleMapsMatrixResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapsApiService {

    @GET("/maps/api/distancematrix/json")
    suspend fun getDistanceMatrix(
        @Query(value="origins") origins: String,
        @Query(value="destinations") destinations: String,
        @Query(value="key") key: String
    ): GoogleMapsMatrixResponse

}

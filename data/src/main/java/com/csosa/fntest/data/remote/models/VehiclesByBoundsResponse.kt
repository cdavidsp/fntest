package com.csosa.fntest.data.remote.models

data class VehiclesByBoundsResponse(
    val poiList: List<VehicleResponse>
)

data class VehicleResponse(
    val id: Long,
    val coordinate: CoordinateResponse,
    val fleetType: String,
    val heading: Float
)
data class CoordinateResponse(
    val latitude: Double,
    val longitude: Double

)

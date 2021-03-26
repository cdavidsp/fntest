package com.csosa.fntest.domain.model

data class Vehicle(
        val id: Long,
        val coordinate: Coordinate,
        val fleetType: FleetType,
        val heading: Float,

        var distanceValue: Double = 0.0,
        var durationValue: Double = 0.0,
        var distance: String = "",
        var duration: String = ""
)
enum class FleetType {
    TAXI,
    POOLING,
}

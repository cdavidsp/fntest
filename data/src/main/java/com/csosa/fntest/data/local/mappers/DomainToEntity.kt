package com.csosa.fntest.data.local.mappers

import com.csosa.fntest.data.local.models.VehicleEntity
import com.csosa.fntest.domain.model.Vehicle

internal fun Vehicle.toEntity(): VehicleEntity {
    return VehicleEntity(
        id = id,
        coordinateLat = coordinate.latitude,
        coordinateLong = coordinate.longitude,
        fleetType = fleetType.name,
        heading = heading,
        durationValue = durationValue,
        duration = duration,
        distanceValue = distanceValue,
        distance = distance
    )
}


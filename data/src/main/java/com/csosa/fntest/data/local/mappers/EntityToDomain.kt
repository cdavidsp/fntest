package com.csosa.fntest.data.local.mappers

import com.csosa.fntest.domain.model.Coordinate
import com.csosa.fntest.domain.model.Vehicle
import com.csosa.fntest.data.local.models.VehicleEntity
import com.csosa.fntest.domain.model.FleetType

internal fun VehicleEntity.toDomain(): Vehicle =
    Vehicle(
        id = id,
        coordinate = Coordinate(coordinateLat, coordinateLong),
        fleetType = FleetType.valueOf(fleetType),
        heading = heading,
        distance = distance,
        distanceValue = distanceValue,
        duration = duration,
        durationValue = durationValue
    )

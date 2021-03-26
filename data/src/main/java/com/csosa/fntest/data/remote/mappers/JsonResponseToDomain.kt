package com.csosa.fntest.data.remote.mappers

import android.content.ContentValues.TAG
import android.util.Log
import com.csosa.fntest.data.remote.models.VehicleResponse
import com.csosa.fntest.domain.model.Coordinate
import com.csosa.fntest.domain.model.FleetType
import com.csosa.fntest.domain.model.Vehicle


internal fun VehicleResponse.toDomain(): Vehicle {

    var fleetTypeEnum = FleetType.TAXI
    try {
        fleetTypeEnum = FleetType.valueOf(fleetType)
    } catch(e: IllegalArgumentException) {
        Log.d(TAG, "INVALID FleetType value: 'Qux' | $e")
    }
    return Vehicle(id, Coordinate(coordinate.latitude, coordinate.longitude), fleetTypeEnum, heading)
}


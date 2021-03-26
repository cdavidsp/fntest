package com.csosa.fntest.mappers

import com.csosa.fntest.R
import com.csosa.fntest.domain.model.FleetType
import com.csosa.fntest.domain.model.Vehicle
import com.csosa.fntest.models.CoordinatePresentation
import com.csosa.fntest.models.VehiclePresentation

internal fun Vehicle.toPresentation(): VehiclePresentation {
    return VehiclePresentation(
        this.id,
        this.fleetType.name,
        CoordinatePresentation(coordinate.latitude, coordinate.longitude),
        this.heading,
        this.duration,
        this.distance,
            if (this.fleetType == FleetType.TAXI)  R.drawable.taxi else R.drawable.pool
    )
}

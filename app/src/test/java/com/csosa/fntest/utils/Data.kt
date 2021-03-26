package com.csosa.fntest.utils

import com.csosa.fntest.domain.model.Coordinate
import com.csosa.fntest.domain.model.FleetType
import com.csosa.fntest.domain.model.Vehicle

object Data {
    val vehicles = mutableListOf(
        Vehicle(
            770827,
            Coordinate(53.63774681071923, 10.077876811952127),
            FleetType.TAXI,
            353.90969927548304f
        ),
        Vehicle(
            485507,
            Coordinate(53.504172654173416, 9.952611993677236),
            FleetType.TAXI,
            77.48096934318164f
        ),
        Vehicle(
            483644,
            Coordinate(53.45314848249459, 10.090219476376275),
            FleetType.TAXI,
            248.89686386855803f
        )
    )

}

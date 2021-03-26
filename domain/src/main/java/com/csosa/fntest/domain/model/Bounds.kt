package com.csosa.fntest.domain.model

data class Bounds(
        val p1: Coordinate,
        val p2: Coordinate
) {
    val center: Coordinate
        get() = Coordinate((p1.latitude + p2.latitude) / 2, (p1.longitude + p2.longitude) / 2)
}

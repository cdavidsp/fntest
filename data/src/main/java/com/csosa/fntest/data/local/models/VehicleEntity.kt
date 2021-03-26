package com.csosa.fntest.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "fleetType") val fleetType: String,
    @ColumnInfo(name = "heading") val heading: Float,
    @ColumnInfo(name = "coordinateLat") val coordinateLat: Double,
    @ColumnInfo(name = "coordinateLong") val coordinateLong: Double,
    // from google api
    @ColumnInfo(name = "durationValue") val durationValue: Double,
    @ColumnInfo(name = "duration") val duration: String,
    @ColumnInfo(name = "distanceValue") val distanceValue: Double,
    @ColumnInfo(name = "distance") val distance: String
)

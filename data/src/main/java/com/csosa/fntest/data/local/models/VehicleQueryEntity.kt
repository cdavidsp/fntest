package com.csosa.fntest.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_queries")
data class VehicleQueryEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "locationLat") val locationLat: Double,
    @ColumnInfo(name = "locationLong") val locationLong: Double,
    @ColumnInfo(name = "p1Lat") val p1Lat: Double,
    @ColumnInfo(name = "p1Long") val p1Long: Double,
    @ColumnInfo(name = "p2Lat") val p2Lat: Double,
    @ColumnInfo(name = "p2Long") val p2Long: Double
)

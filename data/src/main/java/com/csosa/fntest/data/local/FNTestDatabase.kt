package com.csosa.fntest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.csosa.fntest.data.local.dao.VehiclesDao
import com.csosa.fntest.data.local.dao.VehiclesQueriesDao
import com.csosa.fntest.data.local.models.VehicleEntity
import com.csosa.fntest.data.local.models.VehicleQueryEntity

@Database(entities = [VehicleEntity::class, VehicleQueryEntity::class], version = 3, exportSchema = false)
abstract class FNTestDatabase : RoomDatabase() {
    abstract fun vehiclesDao(): VehiclesDao
    abstract fun vehicleQueryDao(): VehiclesQueriesDao
}

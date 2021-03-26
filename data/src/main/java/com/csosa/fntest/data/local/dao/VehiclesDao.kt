package com.csosa.fntest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.csosa.fntest.data.local.models.VehicleEntity


@Dao
interface VehiclesDao {

    @Query("SELECT * FROM vehicles")
    suspend fun getAll(): List<VehicleEntity>


    @Query("SELECT * FROM vehicles WHERE coordinateLat <= :p1Lat AND coordinateLong>= :p1Long AND coordinateLat>=:p2Lat AND coordinateLong<=:p2Long")
    suspend fun getAllByBounds(p1Lat: Double, p1Long: Double, p2Lat: Double, p2Long: Double): List<VehicleEntity>


    @Query("SELECT COUNT(*) FROM vehicles")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vehicles: List<VehicleEntity?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: VehicleEntity?)

    @Query("DELETE FROM vehicles")
    suspend fun deleteAll()

    @Query("DELETE FROM vehicles WHERE id=:id")
    suspend fun deleteById(id: Long)
}

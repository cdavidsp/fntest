package com.csosa.fntest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.csosa.fntest.data.local.models.VehicleEntity
import com.csosa.fntest.data.local.models.VehicleQueryEntity


@Dao
interface VehiclesQueriesDao {

    @Query("SELECT * FROM vehicle_queries")
    suspend fun getAll(): List<VehicleQueryEntity>


    @Query("SELECT * FROM vehicle_queries WHERE p1Lat >= :p1Lat AND p1Long <= :p1Long AND p2Lat <= :p2Lat AND p2Long >= :p2Long")
    suspend fun getAllInside(p1Lat: Double, p1Long: Double, p2Lat: Double, p2Long: Double): List<VehicleQueryEntity>


    @Query("SELECT COUNT(*) FROM vehicle_queries")
    suspend fun getCount(): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vehicles: List<VehicleQueryEntity?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: VehicleQueryEntity?)

    @Query("DELETE FROM vehicle_queries")
    suspend fun deleteAll()

    @Query("DELETE FROM vehicle_queries WHERE id=:id")
    suspend fun deleteById(id: Int)
}

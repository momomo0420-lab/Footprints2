package com.example.footprints2.model.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MyLocationDao {
    @Insert
    suspend fun insert(myLocation: MyLocation)

    @Query("DELETE FROM my_location" )
    suspend fun deleteAll()

    @Query("SELECT * FROM my_location ORDER BY date_and_time DESC")
    fun loadAll(): Flow<List<MyLocation>>

    @Query("SELECT address FROM my_location ORDER BY date_and_time DESC LIMIT 1")
    suspend fun loadLastAddress(): String

    @Query("SELECT * FROM my_location ORDER BY date_and_time DESC")
    suspend fun loadAllMyLocationList(): List<MyLocation>

    @Query("SELECT date_and_time FROM my_location ORDER BY date_and_time DESC")
    suspend fun loadAllDateAndTime(): List<Long>

    @Query("SELECT * FROM my_location " +
                    "WHERE date_and_time > :start AND date_and_time < :end " +
                    "ORDER BY date_and_time DESC")
    suspend fun loadMyLocationListByStartAndEnd(start: Long, end: Long): List<MyLocation>

    @Query("SELECT * FROM my_location " +
                    " WHERE date_and_time = :dateAndTime " +
                    " ORDER BY date_and_time DESC LIMIT 1")
    suspend fun loadMyLocationByDateAndTime(dateAndTime: Long): MyLocation
}
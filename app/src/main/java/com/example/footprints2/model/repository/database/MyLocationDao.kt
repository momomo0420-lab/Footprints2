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

    @Query("SELECT date FROM my_location GROUP BY date ORDER BY date DESC")
    fun loadAllDate(): Flow<List<String>>

    @Query("SELECT * FROM my_location WHERE date = :date ORDER BY time")
    suspend fun loadMyLocationFrom(date: String): List<MyLocation>

    @Query("SELECT address FROM my_location ORDER BY _id DESC LIMIT 1")
    suspend fun loadLastAddress(): String
}
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

    @Query("select * from my_location order by date_and_time desc")
    fun loadAll(): Flow<List<MyLocation>>

    @Query("select address from my_location order by date_and_time desc limit 1")
    suspend fun loadLastAddress(): String
}
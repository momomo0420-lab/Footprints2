package com.example.footprints2.model.repository

import android.location.Location
import androidx.room.Query
import com.example.footprints2.model.repository.database.MyLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    // API操作
    fun getCurrentLocation(listener: (Location) -> Unit)

    // DB操作
    suspend fun insert(location: Location, address: String)
    suspend fun deleteAll()

    fun loadAll(): Flow<List<MyLocation>>

    suspend fun loadLastAddress(): String
    suspend fun loadAllMyLocationList(): List<MyLocation>
    suspend fun loadAllDateAndTime(): List<Long>
    suspend fun loadMyLocationListBySelectedDate(date: Long): List<MyLocation>
    suspend fun loadMyLocationByDateAndTime(dateAndTime: Long): MyLocation
}
package com.example.footprints2.model.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyLocation::class], version = 1, exportSchema = false)
abstract class MyLocationDatabase : RoomDatabase() {
    abstract fun myLocationDao(): MyLocationDao
}
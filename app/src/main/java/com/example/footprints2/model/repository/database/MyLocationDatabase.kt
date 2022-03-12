package com.example.footprints2.model.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.sql.Date

@Database(entities = [MyLocation::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class MyLocationDatabase : RoomDatabase() {
    abstract fun myLocationDao(): MyLocationDao
}

//class Converters {
//    @TypeConverter
//    fun fromTimestamp(value: Long?): Date? {
//        return value?.let { Date(it) }
//    }
//
//    @TypeConverter
//    fun dateToTimestamp(date: Date?): Long? {
//        return date?.time
//    }
//}
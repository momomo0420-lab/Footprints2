package com.example.footprints2.model.repository.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "my_location")
@Parcelize
data class MyLocation(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "date_and_time")
    val dateAndTime: Long
) : Parcelable

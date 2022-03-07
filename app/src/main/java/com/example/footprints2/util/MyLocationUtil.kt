package com.example.footprints2.util

import android.content.Context
import android.location.Geocoder
import android.location.Location

object MyLocationUtil {
    fun convertLocationToAddress(context: Context, location: Location) : String {
        if(!Geocoder.isPresent()) {
            return ""
        }

        val address = Geocoder(context).getFromLocation(
            location.latitude,
            location.longitude,
            1
        )

        return address[0].getAddressLine(0).toString()
    }
}
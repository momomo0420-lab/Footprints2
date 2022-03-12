package com.example.footprints2.util

import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.maps.model.LatLng

object MyLocationUtil {
    /**
     * 座標を住所に変換する
     *
     * @param context コンテキスト
     * @param location 座標
     * @return 住所
     */
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

    /**
     * 住所を座標に変換する
     *
     * @param context コンテキスト
     * @param address 住所
     * @return 座標
     */
    fun convertAddressToLatLng(context: Context, address: String): LatLng {
        if(!Geocoder.isPresent()) {
            return LatLng(-33.852, 151.211)
        }

        val location = Geocoder(context).getFromLocationName(address, 1)[0]

        return LatLng(location.latitude, location.longitude)
    }
}
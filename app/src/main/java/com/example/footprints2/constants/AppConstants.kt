package com.example.footprints2.constants

import android.Manifest

class AppConstants {
    companion object {
        val REQUESTBLE_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val REQUIRED_PERMISSIONS = REQUESTBLE_PERMISSIONS + Manifest.permission.ACCESS_BACKGROUND_LOCATION
    }
}
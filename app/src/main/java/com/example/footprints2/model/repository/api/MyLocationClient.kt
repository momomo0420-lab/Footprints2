package com.example.footprints2.model.repository.api

import android.location.Location

/**
 * ロケーションクライアント
 */
interface MyLocationClient {
    // 現在地を取得する
    fun getCurrentLocation(listener: (Location) -> Unit)
}
package com.example.footprints2.model.repository.api

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * ロケーションクライアントの実装部分
 *
 * @property context コンテキスト
 * @property client ロケーションクライアント
 * @constructor 引数で指定されたコンテキストを基にロケーションのあれこれを行う
 */
class MyLocationClientImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val client: FusedLocationProviderClient
) : MyLocationClient {
    /**
     * 現在地を取得する
     *
     * @param listener リスナー
     */
    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(listener: (Location?) -> Unit) {
        client.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            listener(location)
        }
    }
}
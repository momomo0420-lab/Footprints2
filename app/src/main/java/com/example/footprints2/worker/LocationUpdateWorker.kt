package com.example.footprints2.worker

import android.content.Context
import android.location.Location
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.footprints2.model.repository.LocationRepository
import com.example.footprints2.util.MyLocationUtil
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.*

class LocationUpdateWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: LocationRepository
) : ListenableWorker(context, params) {
    companion object {
        private const val TAG = "LocationUpdateWorker"
        const val UNIQUE_WORK_NAME = "unique_work_name"
    }

    // コルーチンスコープ
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    override fun startWork(): ListenableFuture<Result> {
        return CallbackToFutureAdapter.getFuture { completer ->
            // 現在地を取得
            repository.getCurrentLocation(
                getOnLocationUpdateListener(completer)
            )
        }
    }

    /**
     * ロケーション取得時に起動時の動作
     */
    private fun getOnLocationUpdateListener(
        completer: CallbackToFutureAdapter.Completer<Result>
    ): (Location?) -> Unit {
        return object : (Location?) -> Unit {
            override fun invoke(location: Location?) {
                scope.launch {
                    if(location == null) {
                        completer.set(Result.failure())
                        return@launch
                    }

                    val address = MyLocationUtil.convertLocationToAddress(
                        applicationContext,
                        location
                    )
                    repository.insert(location, address)

                    completer.set(Result.success())
                }
            }
        }
    }

    override fun onStopped() {
        super.onStopped()
        scope.cancel()
    }
}
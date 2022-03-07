package com.example.footprints2.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.footprints2.model.repository.LocationRepository
import javax.inject.Inject

class LocationUpdateWorkerFactory @Inject constructor(
    private val repository: LocationRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return LocationUpdateWorker(
            appContext,
            workerParameters,
            repository
        )
    }
}
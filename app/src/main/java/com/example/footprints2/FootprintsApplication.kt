package com.example.footprints2

import android.app.Application
import androidx.work.Configuration
import com.example.footprints2.model.repository.LocationRepository
import com.example.footprints2.worker.LocationUpdateWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FootprintsApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var repository: LocationRepository

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(LocationUpdateWorkerFactory(repository))
            .build()
    }
}
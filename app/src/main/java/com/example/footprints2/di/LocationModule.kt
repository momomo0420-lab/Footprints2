package com.example.footprints2.di

import android.content.Context
import com.example.footprints2.model.repository.api.MyLocationClient
import com.example.footprints2.model.repository.api.MyLocationClientImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Singleton
    @Provides
    fun provideFusedLocationClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MyLocationClientModule {
    @Singleton
    @Binds
    abstract fun bindMyLocationClient(
        impl: MyLocationClientImpl
    ) : MyLocationClient
}
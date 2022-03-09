package com.example.footprints2.ui.detail

import androidx.lifecycle.ViewModel
import com.example.footprints2.model.repository.LocationRepository
import com.example.footprints2.model.repository.database.MyLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val repository: LocationRepository
) : ViewModel() {
    private var myLocationList: List<MyLocation>? = null

    fun getMyLocationList(): List<MyLocation>? {
        return myLocationList
    }

    suspend fun loadMyLocationListBy(date: Long)  {
        myLocationList = repository.loadMyLocationListBySelectedDate(date)
    }
}
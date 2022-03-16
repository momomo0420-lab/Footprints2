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

    suspend fun getMyLocationList(date: String): List<MyLocation>? {
        if(myLocationList != null) {
            return myLocationList
        }
        myLocationList = repository.loadMyLocationFrom(date)

        return myLocationList
    }
}
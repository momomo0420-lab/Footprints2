package com.example.footprints2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _hasPermissions = MutableLiveData(false)
    val hasPermissions: LiveData<Boolean> get() =  _hasPermissions

    fun setHasPermissions(boolean: Boolean) {
        _hasPermissions.value = boolean
    }
}
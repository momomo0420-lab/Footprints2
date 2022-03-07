package com.example.footprints2.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : ViewModel() {
    private val _text = MutableLiveData("")
    val text: LiveData<String> = _text

    fun setText(str: String) {
        _text.value = str
    }
}
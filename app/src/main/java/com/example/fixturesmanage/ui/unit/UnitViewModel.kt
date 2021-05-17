package com.example.fixturesmanage.ui.unit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UnitViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is unit Fragment"
    }
    val text: LiveData<String> = _text
}
package com.example.basicexample.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val mockHc = HorizCart(
        title1 = "dishidsbiu",
        title2 = "dsc",
        description = "disfdbfgdbtgbbgbgsgbbhidsbiu"
    )

    private val _text = MutableLiveData<HorizCart>().apply {
        value = mockHc
    }
    val text: LiveData<HorizCart> = _text
}

data class HorizCart(
    val title1: String,
    val title2: String,
    val description: String,
)
package com.example.basicexample.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotificationsViewModel : ViewModel() {

    private val _text = MutableStateFlow<String>(value = "This is notifications Fragment7")
    val text: StateFlow<String> = _text.asStateFlow()
}
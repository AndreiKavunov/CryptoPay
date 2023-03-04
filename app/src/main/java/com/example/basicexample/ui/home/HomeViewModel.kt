package com.example.basicexample.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicexample.data.ContentRepository
import com.example.basicexample.data.dto.HorizontalCard
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val contentRepository = ContentRepository()

    private val _text = MutableLiveData<List<HorizontalCard>>()
    val text: LiveData<List<HorizontalCard>> = _text

    fun getHorizontalCards(){
        viewModelScope.launch {

            runCatching {
                contentRepository.getHorizontalCards()
            }.onFailure{
                Log.e("tag1", "ex ${it.message}")
            }.onSuccess {
                _text.postValue(it)
            }

        }
    }
}

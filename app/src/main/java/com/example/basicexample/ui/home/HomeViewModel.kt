package com.example.basicexample.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicexample.data.ContentRepository
import com.example.basicexample.data.dto.AccessToken
import com.example.basicexample.data.dto.FirebaseRepositoryImp
import com.example.basicexample.data.dto.HorizontalCard
import com.example.basicexample.domain.FirebaseRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val contentRepository = ContentRepository()
    private val firebaseRepository = FirebaseRepositoryImp()

    private val _text = MutableLiveData<AccessToken>()
    val text: LiveData<AccessToken> = _text

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

    fun  addCompany() {
        viewModelScope.launch {
            firebaseRepository.addCompanyToFirestore("5", "57566757576567546343545667")
        }
    }

    fun  getCompany() {
        viewModelScope.launch {

            val x = firebaseRepository.getUserFavorites("3")
            Log.d("tag1", "result $x")
        }
    }

}

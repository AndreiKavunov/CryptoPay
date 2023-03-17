package com.example.basicexample.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.basicexample.data.FirebaseRepositoryImp

import com.example.basicexample.domain.models.HorizontalCard
import com.example.basicexample.domain.repository.FirebaseRepository
import com.example.basicexample.domain.usecases.GetHorizontalCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHorizontalCardUseCase: GetHorizontalCardUseCase,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {



    private val _text = MutableLiveData<HorizontalCard>()
    val text: LiveData<HorizontalCard> = _text

    fun getHorizontalCards(){
        viewModelScope.launch {

            runCatching {
                getHorizontalCardUseCase.getHorizontalCard()
            }.onFailure{
                Log.e("tag1", "ex ${it.message}")
            }.onSuccess {
                _text.postValue(it)
            }

        }
    }

    fun  addCompany() {
        viewModelScope.launch {
            firebaseRepository.addCompanyToFirestore("5", "oihnugugk")
        }
    }

    fun  getCompany() {
        viewModelScope.launch {

            val x = firebaseRepository.getUserFavorites("3")
            x.onSuccess {
                Log.d("tag1", "result $it")
            }.onFailure {
                Log.d("tag1", "fail ${it.message}")
            }

        }
    }

}

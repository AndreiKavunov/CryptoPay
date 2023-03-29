package com.example.basicexample.ui.person

import android.util.Log
import androidx.lifecycle.*
import com.example.basicexample.domain.models.PersonInfo
import com.example.basicexample.domain.usecases.AddPersonUseCase
import com.example.basicexample.domain.usecases.GetInfoPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val addPersonUseCase: AddPersonUseCase,
    private val getInfoPersonUseCase: GetInfoPersonUseCase,
) : ViewModel() {

    fun addPerson(person: PersonInfo) = liveData{
            emit(addPersonUseCase.addPerson(person))
    }

    fun getPersonInfo(payId:String) = liveData{
            emit(getInfoPersonUseCase.getInfoPerson(payId))
        }
}
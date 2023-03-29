package com.example.basicexample.ui.person

import android.util.Log
import androidx.lifecycle.*
import com.example.basicexample.domain.models.PersonInfo
import com.example.basicexample.domain.usecases.AddPersonUseCase
import com.example.basicexample.domain.usecases.GetInfoPersonUseCase
import com.example.basicexample.ui.profile.CurrentUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val addPersonUseCase: AddPersonUseCase,
    private val getInfoPersonUseCase: GetInfoPersonUseCase,
) : ViewModel() {

    private val _person = MutableStateFlow<PersonInfo>(
        value = PersonInfo(
            "",
            "", "", "", "", "", "", "", ""
        )
    )
    val person: StateFlow<PersonInfo> = _person.asStateFlow()

    private val _textDialog = MutableStateFlow<String>(value = "")
    val textDialog: StateFlow<String> = _textDialog.asStateFlow()

    fun addPerson(person: PersonInfo){
        viewModelScope.launch {
            addPersonUseCase.addPerson(person).onSuccess {
                _textDialog.emit("Пользователь добавлен")
            }.onFailure {
                _textDialog.emit("ошибка: ${it.message}")
            }
        }
    }

    fun getPersonInfo(payId: String) {
        viewModelScope.launch {
            getInfoPersonUseCase.getInfoPerson(payId).onSuccess {personInfo->
                if(personInfo.binansId == "")personInfo.binansId = "empty"
                _person.emit(personInfo)
            }
        }
    }

    fun changeTextDialog(){
        _textDialog.value = ""
    }
}
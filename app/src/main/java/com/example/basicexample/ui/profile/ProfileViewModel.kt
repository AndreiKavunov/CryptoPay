package com.example.basicexample.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.basicexample.domain.models.Transaction
import com.example.basicexample.domain.usecases.GetTransactionsInDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getTransactionsInDateUseCase: GetTransactionsInDateUseCase,

    ) : ViewModel() {

    fun getTransactions(startTime: String, endTime: String)= liveData{
            emit(getTransactionsInDateUseCase(startTime, endTime))
    }

}

data class CurrentUser(
    val email: String,
    val uid: String,
    val name: String,
    val balance: String,
)
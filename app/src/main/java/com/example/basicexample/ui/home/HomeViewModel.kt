package com.example.basicexample.ui.home

import android.util.Log
import androidx.lifecycle.*

import com.example.basicexample.domain.models.HorizontalCard
import com.example.basicexample.domain.models.PersonInfo
import com.example.basicexample.domain.models.Transaction
import com.example.basicexample.domain.repository.CompanyRepository
import com.example.basicexample.domain.usecases.CreateTransactionUseCase
import com.example.basicexample.domain.usecases.GetBalanceUseCase
import com.example.basicexample.domain.usecases.GetHorizontalCardUseCase
import com.example.basicexample.domain.usecases.GetInfoPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getInfoPersonUseCase: GetInfoPersonUseCase,
) : ViewModel() {

    private val _text = MutableLiveData<HorizontalCard>()
    val text: LiveData<HorizontalCard> = _text

    fun createTransaction(transaction: Transaction, sum: Float){
        viewModelScope.launch {
            createTransactionUseCase.createTransaction(transaction, sum)
        }
    }

    fun getBalance(): LiveData<Result<Float>> = liveData{
        emit(getBalanceUseCase.getBalance())
    }

    fun getPersonInfo(payId: String): LiveData<Result<PersonInfo>> = liveData{
        Log.d("tag1", "getPersonInfo")
        emit(getInfoPersonUseCase.getInfoPerson(payId))
    }

}

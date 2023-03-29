package com.example.basicexample.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.basicexample.domain.models.CompanyInfo

import com.example.basicexample.domain.models.HorizontalCard
import com.example.basicexample.domain.models.PersonInfo
import com.example.basicexample.domain.models.Transaction
import com.example.basicexample.domain.repository.CompanyRepository
import com.example.basicexample.domain.usecases.AddCompanyUseCase
import com.example.basicexample.domain.usecases.CreateNewUserUseCase
import com.example.basicexample.domain.usecases.CreateTransactionUseCase
import com.example.basicexample.domain.usecases.GetBalanceUseCase
import com.example.basicexample.domain.usecases.GetCurrentUserUseCase
import com.example.basicexample.domain.usecases.GetHorizontalCardUseCase
import com.example.basicexample.domain.usecases.GetInfoPersonUseCase
import com.example.basicexample.domain.usecases.GetTransactionsInDateUseCase
import com.example.basicexample.domain.usecases.SingInUseCase
import com.example.basicexample.ui.profile.CurrentUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getInfoPersonUseCase: GetInfoPersonUseCase,

    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val createNewUserUseCase: CreateNewUserUseCase,
    private val singInUseCase: SingInUseCase,
    private val addCompanyUseCase: AddCompanyUseCase,
    private val getTransactionsInDateUseCase: GetTransactionsInDateUseCase,
) : ViewModel() {

    private val _text = MutableLiveData<HorizontalCard>()
    val text: LiveData<HorizontalCard> = _text

    private val _user = MutableStateFlow<CurrentUser>(value = CurrentUser("", "", "", ""))
    val user: StateFlow<CurrentUser> = _user.asStateFlow()

    private val _textDialog = MutableStateFlow<String>(value = "")
    val textDialog: StateFlow<String> = _textDialog.asStateFlow()

    private val _textRegistrationDialog = MutableStateFlow<String>(value = "")
    val textRegistrationDialog: StateFlow<String> = _textRegistrationDialog.asStateFlow()

    init {
        getCurrentUser()
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val result = singInUseCase.signIn(email, password)
            result.onSuccess {
                _textDialog.value = "signIn success"
                if (it.isNotEmpty()) getCurrentUser()
            }.onFailure {
                _textDialog.value = it.message ?: "Error singIn"
            }

        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            val result = getCurrentUserUseCase.getCurrentUser()
            result.onSuccess {
                Log.d("tag1", "getCurrentUser ${it?.email}")
                _user.emit(CurrentUser(it?.email ?: "", it?.uid ?: "", it?.displayName ?: "", ""))
                getBalanceUseCase.getBalance().onSuccess {
                    _user.emit(CurrentUser(email = _user.value.email, uid = _user.value.uid, name = _user.value.name, balance =  it.toString()))
                }

            }
        }
    }

    private fun createTransaction(transaction: Transaction, sum: Float) {
        viewModelScope.launch {
            createTransactionUseCase.createTransaction(transaction, sum).onSuccess {
                _textDialog.value = "Success Transaction"
                getBalanceUseCase.getBalance().onSuccess {
                    _user.emit(CurrentUser(email = _user.value.email, uid = _user.value.uid, name = _user.value.name, balance =  it.toString()))
                }
            }.onFailure {
                _textDialog.value = it.message ?: "Error createTransaction"
            }
        }
    }

    fun getBalance(sum: Float, personId: String) {
        viewModelScope.launch {
            getBalanceUseCase.getBalance().onSuccess { sumBalance ->
                if (sumBalance >= sum) {
                    getPersonInfo(sum = sum, sumBalance = sumBalance, personId = personId)
                } else _textDialog.value = "Недостаточно средств"
            }.onFailure {
                _textDialog.value = it.message ?: "Error getBalance"
            }
        }

    }

    private fun getPersonInfo(sum: Float, sumBalance: Float, personId: String) {
        viewModelScope.launch {
            getInfoPersonUseCase.getInfoPerson(personId).onSuccess { person ->
                if (person.payId.isNotBlank()) {
                    val transaction = Transaction(
                        companyId = "",
                        personId = person.payId,
                        token = "?",
                        date = System.currentTimeMillis().toString(),
                        payId = person.payId,
                        binansId = person.binansId,
                        sumRub = sum.toString(),
                        sumToken = "?"
                    )
                    createTransaction(transaction, sumBalance - sum)
                }else
                    _textDialog.value = "Клиент не найден"
            }.onFailure {
                _textDialog.value = it.message ?: "Error getPersonInfo"
            }
        }
    }

    fun createNewUser(company: CompanyInfo, password: String){
        viewModelScope.launch {
            createNewUserUseCase.createNewUser(company.email, password).onSuccess {
                if (it.isNotEmpty()) {
                    company.id = it
                    addCompanyUseCase.addCompany(company).onSuccess {
                        _textRegistrationDialog.emit("Компания успешно зарегистрирована")
                        getCurrentUser()
                    }.onFailure {
                        _textRegistrationDialog.emit("error ${it.message}")
                    }
                }else{
                    _textRegistrationDialog.emit("error empty id company")
            }
            }.onFailure {
                _textRegistrationDialog.emit("error ${it.message}")
            }
        }
    }

    fun changeTextRegistrationDialog(){
        _textRegistrationDialog.value = ""
    }
}

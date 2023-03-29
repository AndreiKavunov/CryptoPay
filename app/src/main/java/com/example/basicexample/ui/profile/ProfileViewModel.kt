package com.example.basicexample.ui.profile

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.basicexample.domain.models.CompanyInfo
import com.example.basicexample.domain.usecases.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val createNewUserUseCase: CreateNewUserUseCase,
    private val singInUseCase: SingInUseCase,
    private val addCompanyUseCase: AddCompanyUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,

    ) : ViewModel() {

    private val _text = MutableStateFlow<String>(value = "This is notifications Fragment7")
    val text: StateFlow<String> = _text.asStateFlow()

    private val _user = MutableStateFlow<CurrentUser>(value = CurrentUser("", "", ""))
    val user: StateFlow<CurrentUser> = _user.asStateFlow()

    init {
        getCurrentUser()
    }

    fun getBalance(){
        viewModelScope.launch {
            getBalanceUseCase.getBalance().onSuccess {
                Log.d("tag1", "balance ${it.toString()}")
            }
        }
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            val result = getCurrentUserUseCase.getCurrentUser()
            result.onSuccess {
                Log.d("tag1", "getCurrentUser ${it?.email}")
                _user.emit(CurrentUser(it?.email?: "", it?.uid?: "", it?.displayName?: ""))
            }
        }
    }

    fun createNewUser(company: CompanyInfo, password: String) = liveData{

//            val result = createNewUserUseCase.createNewUser(company.email, password)
        createNewUserUseCase.createNewUser(company.email, password).onSuccess {
//                if (it.isNotEmpty()) {
//                    company.id = it
//                    addCompanyUseCase.addCompany(company).onSuccess {
//                        Log.d("tag1", "addCompanyUseCase ${it}")
//                    }.onFailure {
//                        Log.d("tag1", "addCompanyUseCase ${it}")
//                    }
//                }else{
//                    Log.d("tag1", "empty")
//                }
                    company.id = it
                    emit(addCompanyUseCase.addCompany(company))
            }


    }

    fun signIn(email: String, password: String): LiveData<Result<String>> = liveData{
//        viewModelScope.launch {
//            val result = singInUseCase.signIn(email, password)
//            Log.d("tag1", "signIn22 $result")
//            result.onSuccess {
//                Log.d("tag1", "signIn success $it")
//                if (it.isNotEmpty())getCurrentUser()
//
//
//            }
            emit(singInUseCase.signIn(email, password))
//        }
    }

    fun infoUser(){
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid
        }

    }
}

data class CurrentUser(
    val email: String,
    val uid: String,
    val name: String,
)
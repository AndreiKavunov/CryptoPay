package com.example.basicexample.data

import android.util.Log
import com.example.basicexample.domain.models.CompanyInfo
import com.example.basicexample.domain.repository.CompanyRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CompanyRepositoryImp @Inject constructor(private val firebase: Firebase): CompanyRepository {

    companion object {
        const val ROOT_NAME = "company"
        const val COLLECTION_NAME = "name"
    }

    override suspend fun addCompany(company: CompanyInfo): Result<Unit> {
        return runCatching {
           firebase.firestore
                .collection(ROOT_NAME)
                .document(company.id)
                .set(company)
                .await()
        }
    }

    override suspend fun getBalance(): Result<Float> = runCatching {
        val companyId = firebase.auth.currentUser?.uid?: ""
        val result = Firebase.firestore
            .collection(ROOT_NAME)
            .document(companyId)
            .get()
            .await()

        Log.d("tag1", "override suspend fun getBalance ${result.get("sum").toString().toFloat()}")
        result.get("sum").toString().toFloat()
    }

    override suspend fun getUserFavorites(cardNumber: String): Result<String> = runCatching {
        val result = firebase.firestore
            .collection(ROOT_NAME)
            .document(cardNumber)
            .get()
            .await()
        (result.get(COLLECTION_NAME) as? String).orEmpty()
    }

    override suspend fun getCurrentUser(): Result<FirebaseUser?>  = runCatching {
        firebase.auth.currentUser
    }

    override suspend fun createNewUser(email: String, password: String): Result<String> {
        return runCatching {
            val result = firebase.auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.uid?: ""
        }
    }

    override suspend fun signIn(email: String, password: String): Result<String>  = runCatching {
        val result = firebase.auth.signInWithEmailAndPassword(email, password).await()
        result.user?.uid ?: ""
    }

}
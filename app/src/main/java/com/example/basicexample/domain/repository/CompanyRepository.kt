package com.example.basicexample.domain.repository

import com.example.basicexample.domain.models.CompanyInfo
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface CompanyRepository {
    suspend fun addCompany(company: CompanyInfo): Result<Unit>
    suspend fun getUserFavorites(cardNumber: String): Result<String>
    suspend fun getCurrentUser(): Result<FirebaseUser?>
    suspend fun createNewUser(email: String, password: String): Result<String>
    suspend fun signIn(email: String, password: String): Result<String>


    suspend fun getBalance(): Result<Float>

}
package com.example.basicexample.domain.repository

interface FirebaseRepository {
    suspend fun addCompanyToFirestore(userId: String, productId: String)
    suspend fun getUserFavorites(cardNumber: String): Result<String>

}
package com.example.basicexample.domain

interface FirebaseRepository {

//    suspend fun addUserToFirestore(userId: String) : Result<Unit>

    suspend fun addFavoritesToFirestore(userId: String, productId: String) : Result<List<String>>

//    suspend fun removeFavoritesToFirestore(userId: String, productId: String) : Result<List<String>>
//
//    suspend fun getUserFavorites(userId: String) : Result<List<String>>
}
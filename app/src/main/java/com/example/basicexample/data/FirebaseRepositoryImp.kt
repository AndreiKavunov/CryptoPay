package com.example.basicexample.data

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImp {

    companion object {
        const val ROOT_NAME = "company"
        const val COLLECTION_NAME = "name"
    }

    suspend fun addCompanyToFirestore(
        userId: String,
        productId: String
    ) {
        runCatching {
            Firebase.firestore
                .collection(ROOT_NAME)
                .document(userId)
                .update(COLLECTION_NAME, FieldValue.arrayUnion(productId))
                .await()
//            getUserFavorites(userId).getOrThrow()
        }
    }

    suspend fun getUserFavorites(cardNumber: String): Result<String> = runCatching {
        val result = Firebase.firestore
            .collection(ROOT_NAME)
            .document(cardNumber)
            .get()
            .await()
        (result.get(COLLECTION_NAME) as? String).orEmpty()
    }

//    suspend fun getUserFavorites(userId: String): Result<String> {
//        return runCatching {
//            val result = Firebase.firestore.collection(ROOT_NAME).document(userId).get().await()
////            @Suppress("UNCHECKED_CAST")
//            result.get(COLLECTION_NAME) as? String ?: ""
//        }
//
//    }
}
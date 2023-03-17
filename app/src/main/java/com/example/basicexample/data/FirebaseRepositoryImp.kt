package com.example.basicexample.data

import com.example.basicexample.domain.repository.FirebaseRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImp @Inject constructor(): FirebaseRepository {

    companion object {
        const val ROOT_NAME = "company"
        const val COLLECTION_NAME = "name"
    }

    override suspend fun addCompanyToFirestore(
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

    override suspend fun getUserFavorites(cardNumber: String): Result<String> = runCatching {
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
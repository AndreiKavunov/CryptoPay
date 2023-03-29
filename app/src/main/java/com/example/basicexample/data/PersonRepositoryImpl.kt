package com.example.basicexample.data

import android.util.Log
import com.example.basicexample.domain.models.PersonInfo
import com.example.basicexample.domain.repository.CompanyRepository
import com.example.basicexample.domain.repository.PersonRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import okhttp3.internal.threadName
import okhttp3.internal.wait
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(private val firebase: Firebase): PersonRepository {

    companion object {
        const val ROOT_NAME = "person"
    }

    override suspend fun addPerson(
        person: PersonInfo
    ): Result<Unit> {
        return runCatching {
        firebase.firestore
                .collection(ROOT_NAME)
                .document(person.payId)
                .set(person)
                .await()
//
//            firebase.firestore
//                .collection(ROOT_NAME)
//                .add(person)
//                .await()
        }
    }

    override suspend fun getPersonInfo(userId: String): Result<PersonInfo> {
        return runCatching {
            val result = firebase.firestore.collection(ROOT_NAME).document(userId).get().await()
//            val result2 =
//                Firebase.firestore.collection(ROOT_NAME).whereEqualTo("payId", userId).get().await().documents[0]
//
//            val result1 =
//                Firebase.firestore.collection(ROOT_NAME).whereArrayContains("payId", userId).get().await().size()
            PersonInfo(
                name = result.get("name") as? String ?: "",
                surname = result.get("surname") as? String ?: "",
                surname2 = result.get("surname2") as? String ?: "",
                phone = result.get("phone") as? String ?: "",
                payId = result.get("payId") as? String ?: "",
                email = result.get("email") as? String ?: "",
                exchangeAccountNumber = result.get("exchangeAccountNumber") as? String ?: "",
                binansId = result.get("binansId") as? String ?: "",
                dateRegistration = result.get("dateRegistration") as? String ?: "",

            )
//            val test: PersonInfo = result.toObject(PersonInfo)
//            test


        }

    }
}
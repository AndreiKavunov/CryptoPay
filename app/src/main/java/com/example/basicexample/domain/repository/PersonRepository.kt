package com.example.basicexample.domain.repository

import com.example.basicexample.domain.models.PersonInfo
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface PersonRepository {

    suspend fun addPerson(person: PersonInfo): Result<Unit>

    suspend fun getPersonInfo(payId: String): Result<PersonInfo>

}
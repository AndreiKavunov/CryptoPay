package com.example.basicexample.domain.usecases

import com.example.basicexample.domain.models.PersonInfo
import com.example.basicexample.domain.repository.CompanyRepository
import com.example.basicexample.domain.repository.PersonRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

interface GetInfoPersonUseCase {
    suspend fun getInfoPerson(payId: String): Result<PersonInfo>
}

class GetInfoPersonUseCaseImpl @Inject constructor(
    private val personRepository: PersonRepository
): GetInfoPersonUseCase{
    override suspend fun getInfoPerson(payId: String): Result<PersonInfo> {
        return personRepository.getPersonInfo(payId)
    }

}
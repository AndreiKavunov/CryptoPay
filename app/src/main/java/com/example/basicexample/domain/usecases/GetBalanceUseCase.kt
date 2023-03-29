package com.example.basicexample.domain.usecases

import com.example.basicexample.domain.models.PersonInfo
import com.example.basicexample.domain.repository.CompanyRepository
import com.example.basicexample.domain.repository.PersonRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

interface GetBalanceUseCase {
    suspend fun getBalance(): Result<Float>
}

class GetBalanceUseCaseImpl @Inject constructor(
    private val companyRepository: CompanyRepository
): GetBalanceUseCase{
    override suspend fun getBalance(): Result<Float> {
        return companyRepository.getBalance()
    }

}
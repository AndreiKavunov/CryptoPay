package com.example.basicexample.domain.usecases

import com.example.basicexample.domain.models.Transaction
import com.example.basicexample.domain.repository.CompanyRepository
import com.example.basicexample.domain.repository.TransactionRepository
import javax.inject.Inject

interface GetTransactionsInDateUseCase {
    suspend operator fun invoke(startTime: String, endTime: String): Result<List<Transaction>>
}

class GetTransactionsInDateUseCaseImpl @Inject constructor(
    private val transactionRepository: TransactionRepository
): GetTransactionsInDateUseCase{
    override suspend fun invoke(startTime: String, endTime: String): Result<List<Transaction>> {
        return transactionRepository.getTransactionsInDAte(startTime, endTime)
    }

}
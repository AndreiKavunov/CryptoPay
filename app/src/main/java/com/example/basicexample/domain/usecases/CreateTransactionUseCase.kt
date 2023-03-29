package com.example.basicexample.domain.usecases

import com.example.basicexample.domain.models.Transaction
import com.example.basicexample.domain.repository.CompanyRepository
import com.example.basicexample.domain.repository.TransactionRepository
import javax.inject.Inject


interface CreateTransactionUseCase {
    suspend fun createTransaction(transaction: Transaction, sum: Float): Result<Unit>
}

class CreateTransactionUseCaseImpl @Inject constructor(
    private val transactionRepository: TransactionRepository
): CreateTransactionUseCase{
    override suspend fun createTransaction(transaction: Transaction, sum: Float): Result<Unit> {
        return transactionRepository.createTransaction(transaction, sum)
    }

}
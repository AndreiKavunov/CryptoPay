package com.example.basicexample.domain.repository

import com.example.basicexample.domain.models.Transaction

interface TransactionRepository {

    suspend fun createTransaction(transaction: Transaction, sum: Float): Result<Unit>
}
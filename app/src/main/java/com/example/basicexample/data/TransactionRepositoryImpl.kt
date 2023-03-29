package com.example.basicexample.data

import com.example.basicexample.domain.models.Transaction
import com.example.basicexample.domain.repository.CompanyRepository
import com.example.basicexample.domain.repository.TransactionRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
class TransactionRepositoryImpl @Inject constructor(private val firebase: Firebase):
    TransactionRepository {

    companion object {
        const val COMPANY = "company"
        const val TRANSACTION = "transaction"
    }

    override suspend fun createTransaction(transaction: Transaction, sum: Float): Result<Unit> {
        return runCatching {

            val companyId = firebase.auth.currentUser?.uid ?: ""

            transaction.companyId = companyId
            firebase.firestore
                .collection(TRANSACTION)
                .add(transaction)
                .await()


            firebase.firestore
                .collection(COMPANY)
                .document(companyId)
                .update("sum", sum)
                .await()

        }
    }
}
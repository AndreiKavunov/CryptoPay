package com.example.basicexample.domain.usecases

import com.example.basicexample.domain.repository.CompanyRepository
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

interface SingInUseCase {
    suspend fun signIn(email: String, password: String): Result<String>
}

class SingInUseCaseImpl @Inject constructor(
    private val firebaseRepository: CompanyRepository
): SingInUseCase{
    override suspend fun signIn(email: String, password: String): Result<String> {
        return firebaseRepository.signIn(email, password)
    }

}
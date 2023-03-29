package com.example.basicexample.domain.usecases

import com.example.basicexample.domain.repository.CompanyRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

interface GetCurrentUserUseCase {
    suspend fun getCurrentUser(): Result<FirebaseUser?>
}

class GetCurrentUserUseCaseImpl @Inject constructor(
    private val firebaseRepository: CompanyRepository
): GetCurrentUserUseCase{
    override suspend fun getCurrentUser(): Result<FirebaseUser?> {
        return firebaseRepository.getCurrentUser()
    }

}
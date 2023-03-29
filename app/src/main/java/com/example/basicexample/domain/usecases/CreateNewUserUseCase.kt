package com.example.basicexample.domain.usecases

import com.example.basicexample.domain.models.CompanyInfo
import com.example.basicexample.domain.repository.CompanyRepository
import javax.inject.Inject

interface CreateNewUserUseCase {
    suspend fun createNewUser(email: String, password: String): Result<String>
}

class CreateNewUserUseCaseImpl @Inject constructor(
    private val companyRepository: CompanyRepository
): CreateNewUserUseCase{
    override suspend fun createNewUser(email: String, password: String): Result<String> {
        return companyRepository.createNewUser(email, password)
    }

}
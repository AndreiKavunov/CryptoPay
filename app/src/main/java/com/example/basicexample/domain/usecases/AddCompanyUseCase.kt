package com.example.basicexample.domain.usecases

import com.example.basicexample.domain.models.CompanyInfo
import com.example.basicexample.domain.models.PersonInfo
import com.example.basicexample.domain.repository.CompanyRepository
import com.example.basicexample.domain.repository.PersonRepository
import javax.inject.Inject

interface AddCompanyUseCase {
    suspend fun addCompany(company: CompanyInfo): Result<Unit>
}

class AddCompanyUseCaseImpl @Inject constructor(
    private val companyRepository: CompanyRepository
): AddCompanyUseCase{
    override suspend fun addCompany(company: CompanyInfo): Result<Unit> {
        return companyRepository.addCompany(company)
    }

}
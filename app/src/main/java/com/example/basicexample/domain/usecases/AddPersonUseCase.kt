package com.example.basicexample.domain.usecases

import com.example.basicexample.domain.models.PersonInfo
import com.example.basicexample.domain.repository.CompanyRepository
import com.example.basicexample.domain.repository.PersonRepository
import javax.inject.Inject

interface AddPersonUseCase {
    suspend fun addPerson(person: PersonInfo): Result<Unit>
}

class AddPersonUseCaseImpl @Inject constructor(
    private val personRepository: PersonRepository
): AddPersonUseCase{
    override suspend fun addPerson(person: PersonInfo): Result<Unit> {
        return personRepository.addPerson(person)
    }

}
package com.example.basicexample.domain.usecases

import com.example.basicexample.domain.models.HorizontalCard
import com.example.basicexample.domain.repository.ContentRepository
import javax.inject.Inject

interface GetHorizontalCardUseCase {
    suspend fun getHorizontalCard(): HorizontalCard
}

class GetHorizontalCardUseCaseImpl @Inject constructor(
    private val contentRepository: ContentRepository
): GetHorizontalCardUseCase{
    override suspend fun getHorizontalCard(): HorizontalCard {
        return contentRepository.getHorizontalCards()
    }

}
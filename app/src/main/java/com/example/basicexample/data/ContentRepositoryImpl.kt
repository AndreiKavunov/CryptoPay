package com.example.basicexample.data

import com.example.basicexample.data.dto.AccessToken
import com.example.basicexample.domain.models.HorizontalCard
import com.example.basicexample.domain.repository.ContentRepository
import javax.inject.Inject


class ContentRepositoryImpl @Inject constructor(
    private val openComApi: OpenComApi
) : ContentRepository {

    override suspend fun getHorizontalCards(): HorizontalCard {

        return openComApi.getAccessToken().toDomainModel()
    }
}

fun AccessToken.toDomainModel(): HorizontalCard{
    return HorizontalCard(
        title = this.expiresIn.toString(),
        description = this.accessToken.toString()
    )
}
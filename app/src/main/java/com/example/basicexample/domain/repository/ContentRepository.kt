package com.example.basicexample.domain.repository

import com.example.basicexample.data.dto.AccessToken
import com.example.basicexample.domain.models.HorizontalCard

interface ContentRepository{
    suspend fun getHorizontalCards(): HorizontalCard
}
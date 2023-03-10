package com.example.basicexample.di

import com.example.basicexample.domain.usecases.GetHorizontalCardUseCase
import com.example.basicexample.domain.usecases.GetHorizontalCardUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class )
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetHorizontalCardUseCase(impl: GetHorizontalCardUseCaseImpl): GetHorizontalCardUseCase

}
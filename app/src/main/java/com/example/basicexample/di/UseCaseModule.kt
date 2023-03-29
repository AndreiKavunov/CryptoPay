package com.example.basicexample.di

import com.example.basicexample.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class )
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetHorizontalCardUseCase(impl: GetHorizontalCardUseCaseImpl): GetHorizontalCardUseCase

    @Binds
    abstract fun bindGetCurrentUserUseCase(impl: GetCurrentUserUseCaseImpl): GetCurrentUserUseCase

    @Binds
    abstract fun bindCreateNewUserUseCase(impl: CreateNewUserUseCaseImpl): CreateNewUserUseCase

    @Binds
    abstract fun bindSingInUseCase(impl: SingInUseCaseImpl): SingInUseCase

    @Binds
    abstract fun bindAddPersonUseCase(impl: AddPersonUseCaseImpl): AddPersonUseCase

    @Binds
    abstract fun bindGetInfoPersonUseCase(impl: GetInfoPersonUseCaseImpl): GetInfoPersonUseCase
    @Binds
    abstract fun bindAddCompanyUseCase(impl: AddCompanyUseCaseImpl): AddCompanyUseCase

    @Binds
    abstract fun bindGetBalanceUseCase(impl: GetBalanceUseCaseImpl): GetBalanceUseCase
    @Binds
    abstract fun bindCreateTransactionUseCase(impl: CreateTransactionUseCaseImpl): CreateTransactionUseCase

}
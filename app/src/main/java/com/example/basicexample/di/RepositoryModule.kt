package com.example.basicexample.di


import com.example.basicexample.data.ContentRepositoryImpl
import com.example.basicexample.data.CompanyRepositoryImp
import com.example.basicexample.data.PersonRepositoryImpl
import com.example.basicexample.data.TransactionRepositoryImpl
import com.example.basicexample.domain.repository.ContentRepository
import com.example.basicexample.domain.repository.CompanyRepository
import com.example.basicexample.domain.repository.PersonRepository
import com.example.basicexample.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class )
abstract class RepositoryModule {

    @Binds
    abstract fun bindContentRepository(impl: ContentRepositoryImpl): ContentRepository

    @Binds
    abstract fun bindCompanyRepository(impl: CompanyRepositoryImp): CompanyRepository

    @Binds
    abstract fun bindPersonRepository(impl: PersonRepositoryImpl): PersonRepository

    @Binds
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

}
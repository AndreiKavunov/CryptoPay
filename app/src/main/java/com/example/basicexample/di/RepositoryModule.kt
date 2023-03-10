package com.example.basicexample.di


import com.example.basicexample.data.ContentRepositoryImpl
import com.example.basicexample.domain.repository.ContentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class )
abstract class RepositoryModule {

    @Binds
    abstract fun bindContentRepository(impl: ContentRepositoryImpl): ContentRepository

}
package com.example.basicexample.di

import com.example.basicexample.data.OpenComApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class )
class NetworkModule {

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun providesJson(): Json {
        return Json{
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    @Provides
    fun providesRetrofit(client: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://api.decathlon.ru/bitrix-backend/api/")
            .build()
    }

    @Provides
    fun providesOpenComApi(openComRetrofit: Retrofit): OpenComApi {
        return openComRetrofit
            .create(OpenComApi::class.java)
    }

    @Provides
    @Singleton
    fun providesFirebase(): Firebase{
        return Firebase
    }

}
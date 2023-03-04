package com.example.basicexample.data

import com.example.basicexample.data.dto.AccessToken
import com.example.basicexample.data.dto.HorizontalCard
import retrofit2.http.GET
import retrofit2.http.Header

interface OpenComApi {

    @GET("https://api.dev.decathlon.ru/bitrix-backend/api/v1/oauth/token")
    suspend fun getAccessToken(): AccessToken

    @GET("https://api.dev.decathlon.ru/bitrix-backend/api/v1/content/main/horizontal-cards")
    suspend fun getHorizontalCard(
        @Header("region") region: String= "moscow",
        @Header("channel") channel: String= "web",
        @Header("locale") locale: String= "ru-Ru",
//        @Header("Authorization") breaer: String
    ): List<HorizontalCard>

    @GET("https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=d90de2e6c8af42180001bca96f65371d")
    suspend fun getWeather(): AccessToken
}
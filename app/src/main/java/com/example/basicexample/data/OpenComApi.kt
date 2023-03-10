package com.example.basicexample.data

import com.example.basicexample.data.dto.AccessToken
import retrofit2.http.GET

interface OpenComApi {

    @GET("https://api.dev.decathlon.ru/bitrix-backend/api/v1/oauth/token")
    suspend fun getAccessToken(): AccessToken

//    @GET("https://api.dev.decathlon.ru/bitrix-backend/api/v1/content/main/horizontal-cards")
//    suspend fun getHorizontalCard(
//        @Header("region") region: String= "moscow",
//        @Header("channel") channel: String= "web",
//        @Header("locale") locale: String= "ru-Ru",
////        @Header("Authorization") breaer: String
//    ): List<HorizontalCard>

}
package com.example.basicexample.data

import android.util.Log
import com.example.basicexample.App
import com.example.basicexample.data.dto.HorizontalCard
import okhttp3.OkHttpClient

class ContentRepository {

    suspend fun getHorizontalCards(): List<HorizontalCard>{

        val token = App.openComApi.getAccessToken()

        Log.e("tag1", "token $token")

//        return App.openComApi.getHorizontalCard(breaer = "Bearer ${token.accessToken}")
        return App.openComApi.getHorizontalCard()
    }
}
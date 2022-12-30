package com.call.screen.themes.interfaces

import com.call.screen.themes.data.model.CallModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


interface ApiInterface {
    @GET("api/free")
    fun getFreeCallVideos():Call<CallModel>
    companion object {
        var BASE_URL = "https://callapi.onrender.com/"
        fun create() : ApiInterface {
            val client = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}
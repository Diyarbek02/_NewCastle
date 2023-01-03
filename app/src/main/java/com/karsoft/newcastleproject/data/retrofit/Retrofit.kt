package com.karsoft.newcastleproject.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl("http://Yusupog4.beget.tech/api/v1")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
package com.karsoft.newcastleproject.data.retrofit

import com.karsoft.newcastleproject.core.loginRequest
import com.karsoft.newcastleproject.core.loginResponse
import com.karsoft.newcastleproject.data.models.request.Registration
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/auth/register")
    suspend fun registerUser(
        @Body user : Registration
    ): Response<loginResponse>

    @POST("/auth/login")
    suspend fun login(
        @Body user: loginRequest
    ): Response<loginResponse>


}
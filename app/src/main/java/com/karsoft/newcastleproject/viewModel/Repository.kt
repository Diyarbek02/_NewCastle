package com.karsoft.newcastleproject.viewModel

import com.karsoft.newcastleproject.data.models.request.Login
import com.karsoft.newcastleproject.data.models.request.Registration
import com.karsoft.newcastleproject.data.retrofit.ApiService
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun login(login: Login) = apiService.login(login)

    suspend fun registerUser(user: Registration) = apiService.registerUser(user)
}
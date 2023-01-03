package com.karsoft.newcastleproject.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karsoft.newcastleproject.core.NetworkResult
import com.karsoft.newcastleproject.core.loginRequest
import com.karsoft.newcastleproject.core.loginResponse
import com.karsoft.newcastleproject.data.models.request.Registration
import com.karsoft.newcastleproject.data.retrofit.RetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository): ViewModel(){

    private val apiService = RetrofitService.apiService

    private val _registerUser: MutableLiveData<NetworkResult<com.karsoft.newcastleproject.data.models.response.Login>> = MutableLiveData()
    val registerUser: LiveData<NetworkResult<com.karsoft.newcastleproject.data.models.response.Login>> = _registerUser

    fun registerUser(user: Registration) = viewModelScope.launch {
        repository.registerUser(user).let { response ->
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _registerUser.value = NetworkResult.Success(it)
                    }
                }else {
                    _registerUser.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _registerUser.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }

    private val _login: MutableLiveData<NetworkResult<loginResponse>> = MutableLiveData()
    val login: LiveData<NetworkResult<loginResponse>> = _login

    fun login(user: loginRequest) {
        _login.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.login(user)

                if (response.isSuccessful) {
                    response.body()?.let { login1: loginResponse ->
                        _login.value = NetworkResult.Success(login1)
                    }
                } else {
                    _login.value = NetworkResult.Error(response.message())
                }
            } catch (e: Exception) {
                _login.value = NetworkResult.Error(e.localizedMessage)
            }
        }
    }
}
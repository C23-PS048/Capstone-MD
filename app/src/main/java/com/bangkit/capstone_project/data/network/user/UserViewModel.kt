package com.bangkit.capstone_project.data.network.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone_project.data.network.config.ApiConfig
import com.bangkit.capstone_project.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserViewModel(private val repository: UserRepository) : ViewModel() {


    private val _responseState = MutableStateFlow<UiState<ResponseMessage>?>(null)
    val responseState: StateFlow<UiState<ResponseMessage>?> = _responseState

    private val _loginState = MutableStateFlow<UiState<LoginResponse>?>(null)
    val loginState: StateFlow<UiState<LoginResponse>?> = _loginState
    private val _uiState: MutableStateFlow<UiState<DetailUserResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DetailUserResponse>>
        get() = _uiState

    fun resetResponseState() {
        _responseState.value = null
        _loginState.value = null

    }

    fun registerUser(name: String, email: String, password: String) {
        _responseState.value = UiState.Loading
        viewModelScope.launch {

            try {
                val response = ApiConfig.getUserService().registerUser(name, email, password)

                Log.d("TAG", "registerUser: ${response.toString()}")
                if (response.body()?.error == false) {

                    _responseState.value = UiState.Success(response.body())
                } else {
                    _responseState.value = UiState.Error(response.toString() ?: "Unknown error")
                }
            } catch (e: HttpException) {
                _responseState.value = UiState.Error(e.message ?: "Unknown error")
            } catch (e: Exception) {
                _responseState.value = UiState.Error(e.message ?: "Unknown error")
            }
            Log.d("TAG", "registerUser: ${responseState.value}")
        }
    }

    fun loginUser(email: String, password: String) {
        _loginState.value = UiState.Loading
        viewModelScope.launch {

            try {
                val response = ApiConfig.getUserService().loginUser(email, password)

                Log.d("TAG", "registerUser: ${response.toString()}")
                if (response.isSuccessful) {
                    if (response.body()?.error == false) {

                        _loginState.value = UiState.Success(response.body())
                    } else {
                        _loginState.value = UiState.Error(response.toString() ?: "Unknown error")
                    }
                } else {
                    _loginState.value = UiState.Error(response.toString() ?: "Unknown error")
                }
            } catch (e: HttpException) {
                _loginState.value = UiState.Error(e.message ?: "Unknown error")
            } catch (e: Exception) {
                _loginState.value = UiState.Error(e.message ?: "Unknown error")
            }
            Log.d("TAG", "registerUser: ${loginState.value}")
        }
    }

    fun getUser(id: String, token: String) {
        _uiState.value = UiState.Loading
        val token = "Bearer $token"


        viewModelScope.launch {


            try {
                val response = ApiConfig.getUserService().getUserInfo(id, token)

                Log.d("TAG", "registerUser: ${response.toString()}")
                if (response.isSuccessful) {

                    _uiState.value = UiState.Success(response.body())
                } else {
                    _uiState.value = UiState.Error(response.toString() ?: "Unknown error")
                }
            } catch (e: HttpException) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
            Log.d("TAG", "registerUser: ${uiState.value}")
        }
    }

}
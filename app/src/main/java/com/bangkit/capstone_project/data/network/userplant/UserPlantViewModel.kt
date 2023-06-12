package com.bangkit.capstone_project.data.network.userplant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone_project.data.network.config.ApiConfig
import com.bangkit.capstone_project.data.network.user.ResponseMessage
import com.bangkit.capstone_project.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserPlantViewModel() : ViewModel() {
    private val _userPlant: MutableStateFlow<UiState<UserPlantResponse>> =
        MutableStateFlow(UiState.Loading)
    val userPlant: StateFlow<UiState<UserPlantResponse>>
        get() = _userPlant

    private val _responseState = MutableStateFlow<UiState<ResponseMessage>?>(null)
    val responseState: StateFlow<UiState<ResponseMessage>?> = _responseState
    private val _uiState: MutableStateFlow<UiState<TaskResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<TaskResponse>>
        get() = _uiState

    fun resetResponseState() {
        _responseState.value = null


    }

    fun getUserPlant(id: String, token: String) {
        _userPlant.value = UiState.Loading
        val token = "Bearer $token"
        viewModelScope.launch {
            try {
                val response = ApiConfig.getUserService().getUserPlant(id, token)

                Log.d("TAG", "registerUser: ${response.toString()}")
                if (response.isSuccessful) {

                    _userPlant.value = UiState.Success(response.body())
                } else {
                    _userPlant.value = UiState.Error(response.toString() ?: "Unknown error")
                }
            } catch (e: HttpException) {
                _userPlant.value = UiState.Error(e.message ?: "Unknown error")
            } catch (e: Exception) {
                _userPlant.value = UiState.Error(e.message ?: "Unknown error")
            }
            Log.d("TAG", "registerUser: ${userPlant.value}")
        }
    }


    fun saveUserPlant(
        location: String,
        disease: String,
        startDate: String,
        lastScheduledDate: String,
        nextScheduledDate: String,
        frequency: String,
        plantId: String,
        userId: String,
        token: String
    ) {
        val headerToken = "Bearer $token"
        viewModelScope.launch {
            try {
                val response = ApiConfig.getUserPlantService().saveUserPlant(
                    location = location,
                    disease = disease,
                    startDate = startDate,
                    lastScheduledDate = lastScheduledDate,
                    nextScheduledDate = nextScheduledDate,
                    frequency = frequency,
                    plantId = plantId,
                    userId = userId,
                    token = headerToken
                )
                if (response.isSuccessful) {
                    val message = response.body()
                    _responseState.value = UiState.Success(message)
                    _userPlant.value = UiState.Loading
                } else {
                    val errorMessage = "Failed to save plant"
                    _responseState.value = UiState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = "An error occurred"
                _responseState.value = e.message?.let { UiState.Error(it) }
                Log.d("TAG", "saveUserPlant: ${responseState.value}")
            }
        }
    }

    fun getPlant(
        id: Int,
        token: String
    ) {
        val headerToken = "Bearer $token"
        viewModelScope.launch {
            try {
                val response = ApiConfig.getUserPlantService().getUserPlantInfo(
                    id = id,
                    token = headerToken
                )
                if (response.isSuccessful) {
                    val message = response.body()
                    _uiState.value = UiState.Success(message)
                } else {
                    val errorMessage = "Failed to save plant"
                    _uiState.value = UiState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = "An error occurred"
                _uiState.value = e.message?.let { UiState.Error(it) }!!
                Log.d("TAG", "saveUserPlant: ${uiState.value}")
            }
        }
    }
    fun updateTask(
        id:String,
        location: String,
        disease: String,
        startDate: String,
        lastScheduledDate: String,
        nextScheduledDate: String,
        frequency: String,
        plantId: String,
        userId: String,
        token: String
    ) {
        val headerToken = "Bearer $token"
        viewModelScope.launch {
            try {
                val response = ApiConfig.getUserPlantService().updateTask(
                    id=id,
                    location = location,
                    disease = disease,
                    startDate = startDate,
                    lastScheduledDate = lastScheduledDate,
                    nextScheduledDate = nextScheduledDate,
                    frequency = frequency,
                    plantId = plantId,
                    userId = userId,
                    token = headerToken
                )
                if (response.isSuccessful) {
                    val message = response.body()
                    _responseState.value = UiState.Success(message)
                    _uiState.value = UiState.Loading
                } else {
                    val errorMessage = "Failed to save plant"
                    _responseState.value = UiState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = "An error occurred"
                _responseState.value = e.message?.let { UiState.Error(it) }
                Log.d("TAG", "saveUserPlant: ${responseState.value}")
            }
        }
    }
    fun deletePlant(
        id: Int,
        token: String
    ) {
        val headerToken = "Bearer $token"
        viewModelScope.launch {
            try {
                val response = ApiConfig.getUserPlantService().deleteTask(
                    id = id,
                    token = headerToken
                )
                if (response.isSuccessful) {
                    val message = response.body()
                    _responseState.value = UiState.Success(message)
                    _userPlant.value = UiState.Loading
                } else {
                    val errorMessage = "Failed to Delete plant"
                    _responseState.value = UiState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = "An error occurred"
                _responseState.value = e.message?.let { UiState.Error(it) }!!
                Log.d("TAG", "saveUserPlant: ${responseState.value}")
            }
        }
    }

}
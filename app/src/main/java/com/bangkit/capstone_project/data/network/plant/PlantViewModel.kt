package com.bangkit.capstone_project.data.network.plant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone_project.data.network.config.ApiConfig
import com.bangkit.capstone_project.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PlantViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<PlantResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<PlantResponse>>
        get() = _uiState

    private val _plantState: MutableStateFlow<UiState<SinglePlantResponse>> =
        MutableStateFlow(UiState.Loading)
    val plantState: StateFlow<UiState<SinglePlantResponse>>
        get() = _plantState

    fun getAll() {

        _uiState.value = UiState.Loading



        viewModelScope.launch {


            try {
                val response = ApiConfig.getPlantService().getAllPlant()

                Log.d("TAG", "Plants: ${response.toString()}")
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
            Log.d("TAG", "Plants: ${uiState.value}")
        }
    }

    fun getPlant(slug: String, token: String) {
        val headerToken = "Bearer $token"
        _plantState.value = UiState.Loading



        viewModelScope.launch {


            try {
                val response = ApiConfig.getPlantService().getPlant(slug)

                Log.d("TAG", "Plants: ${response.toString()}")
                if (response.isSuccessful) {

                    _plantState.value = UiState.Success(response.body())
                } else {
                    _plantState.value = UiState.Error(response.toString() ?: "Unknown error")
                }
            } catch (e: HttpException) {
                _plantState.value = UiState.Error(e.message ?: "Unknown error")
            } catch (e: Exception) {
                _plantState.value = UiState.Error(e.message ?: "Unknown error")
            }
            Log.d("TAG", "Plants: ${plantState.value}")
        }
    }


}
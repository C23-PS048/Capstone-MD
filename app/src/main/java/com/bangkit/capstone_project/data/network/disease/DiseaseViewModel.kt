package com.bangkit.capstone_project.data.network.disease

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone_project.data.network.config.ApiConfig
import com.bangkit.capstone_project.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DiseaseViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<AllDiseaseResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<AllDiseaseResponse>>
        get() = _uiState

    private val _diseaseState: MutableStateFlow<UiState<DiseaseResponse>> =
        MutableStateFlow(UiState.Loading)
    val diseaseState: StateFlow<UiState<DiseaseResponse>>
        get() = _diseaseState
    fun resetResponseState() {
        _diseaseState.value = UiState.Loading


    }
    fun getAlLDisease() {

        _uiState.value = UiState.Loading



        viewModelScope.launch {


            try {
                val response = ApiConfig.getDiseaseService().getAlLDisease()

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

    fun getDisease(slug: String) {
        _diseaseState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = ApiConfig.getDiseaseService().getDisease(slug)

                Log.d("TAG", "Plants: ${response.toString()}")
                if (response.isSuccessful) {

                    _diseaseState.value = UiState.Success(response.body())
                } else {
                    _diseaseState.value = UiState.Error(response.toString() ?: "Unknown error")
                }
            } catch (e: HttpException) {
                _diseaseState.value = UiState.Error(e.message ?: "Unknown error")
            } catch (e: Exception) {
                _diseaseState.value = UiState.Error(e.message ?: "Unknown error")
            }
            Log.d("TAG", "Plants: ${diseaseState.value}")
        }
    }


}
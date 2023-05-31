package com.bangkit.capstone_project.network.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone_project.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Response>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Response>>
        get() = _uiState
    fun getWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _uiState.value = UiState.Success( repository.getWeather(latitude,longitude))

        }
    }
}
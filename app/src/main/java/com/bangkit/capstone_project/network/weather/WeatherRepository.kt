package com.bangkit.capstone_project.network.weather

import androidx.lifecycle.MutableLiveData

class WeatherRepository {




    suspend fun getWeather(latitude: Double, longitude: Double): Response {
        return WeatherConfig.getWeatherService().getCurrentWeather(latitude, longitude)
    }
    companion object {
        @Volatile
        private var instance: WeatherRepository? = null

        fun getInstance(): WeatherRepository =
            instance ?: synchronized(this) {
                WeatherRepository().apply {
                    instance = this
                }
            }
    }
}
package com.bangkit.capstone_project.data.network.weather

import com.bangkit.capstone_project.data.network.config.ApiConfig

class WeatherRepository {




    suspend fun getWeather(latitude: Double, longitude: Double): WeatherResponse {
        return ApiConfig.getWeatherService().getCurrentWeather(latitude, longitude)
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
package com.bangkit.capstone_project.data.network.weather

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appKey: String = "c2614ba22acd9771656c4dc650f6cba4",
        @Query("units") units: String = "metric",
    ): WeatherResponse
}
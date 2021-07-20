package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/forecast")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("appId") appId: String,
        @Query("units") units: String
    ): Response<WeatherResponse>
}
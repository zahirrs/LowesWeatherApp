package com.example.weatherapp.repository

import com.example.weatherapp.network.ApiHelper

class WeatherRepository(private val apiHelper: ApiHelper) {

    suspend fun getWeather(city: String) = apiHelper.getWeather(city)

}
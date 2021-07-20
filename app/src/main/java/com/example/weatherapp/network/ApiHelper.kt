package com.example.weatherapp.network

class ApiHelper(private val weatherService: WeatherService) {

    suspend fun getWeather(city: String) =
        weatherService.getWeather(
            city,
            "65d00499677e59496ca2f318eb68c049",
            "imperial"
        )
}
package com.example.weatherapp.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Coordinates(
    val lat: Double?,
    val lon: Double?
)
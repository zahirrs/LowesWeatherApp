package com.example.weatherapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rain(
    @field:Json(name = "3h") val `3h`: Double?
)
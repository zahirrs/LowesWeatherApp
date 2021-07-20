package com.example.weatherapp.viewmodel

import androidx.lifecycle.*
import com.example.weatherapp.model.HourlyWeather
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    private var _weatherResponse: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()
    val weatherResponse: LiveData<Resource<WeatherResponse>> get() = _weatherResponse

    var shouldNavigate = false
    var selectedWeather: HourlyWeather? = null

    var cityNameInput = ""

    fun fetchWeather() = viewModelScope.launch(Dispatchers.IO) {
        if (cityNameInput.isBlank()) {
            _weatherResponse.postValue(Resource.Error("Enter a city name"))
        }
        _weatherResponse.postValue(Resource.Loading)
        try {
            val response = weatherRepository.getWeather(cityNameInput)
            val weatherResponse =
                if (response.isSuccessful && response.body() != null) {
                    shouldNavigate = true
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("Results could not be found.")
                }
            _weatherResponse.postValue(weatherResponse)
        } catch (ex: Exception) {
            _weatherResponse.postValue(Resource.Error(ex.toString()))
        }
    }
}

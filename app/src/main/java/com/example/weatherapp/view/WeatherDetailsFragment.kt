package com.example.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.databinding.FragmentWeatherResultsBinding
import com.example.weatherapp.model.HourlyWeather
import com.example.weatherapp.network.ApiHelper
import com.example.weatherapp.network.RetrofitBuilder
import com.example.weatherapp.viewmodel.ViewModelFactory
import com.example.weatherapp.viewmodel.WeatherViewModel

class WeatherDetailsFragment : Fragment() {

    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by activityViewModels {
        ViewModelFactory(ApiHelper(RetrofitBuilder.weatherService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            weatherViewModel.selectedWeather?.let { hourlyWeather ->
                tvCurrent.text = hourlyWeather.main?.temp?.toInt().toString()
                tvFeelsLike.text = String.format(
                    getString(R.string.feels_like),
                    hourlyWeather.main?.feelsLike?.toInt().toString()
                )
                tvMain.text = hourlyWeather.weather[0].main
                tvDescription.text = hourlyWeather.weather[0].description
                Glide.with(requireContext())
                    .load(("https://openweathermap.org/img/w/${hourlyWeather.weather[0].icon}.png"))
                    .into(weatherImage)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
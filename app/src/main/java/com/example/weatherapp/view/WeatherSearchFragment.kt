package com.example.weatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCitySearchBinding
import com.example.weatherapp.network.ApiHelper
import com.example.weatherapp.network.RetrofitBuilder
import com.example.weatherapp.utils.Resource
import com.example.weatherapp.viewmodel.ViewModelFactory
import com.example.weatherapp.viewmodel.WeatherViewModel

class WeatherSearchFragment : Fragment() {

    private var _binding: FragmentCitySearchBinding? = null
    private val binding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by activityViewModels {
        ViewModelFactory(ApiHelper(RetrofitBuilder.weatherService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitySearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnSearch.setOnClickListener {
                weatherViewModel.fetchWeather()
            }

            etCity.addTextChangedListener {
                weatherViewModel.cityNameInput = it.toString()
            }

            weatherViewModel.weatherResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        cityEt.error = null
                        etCity.isEnabled = false
                        btnSearch.isEnabled = false
                    }
                    is Resource.Error -> {
                        cityEt.error = it.errorMsg
                        etCity.isEnabled = true
                        btnSearch.isEnabled = true
                    }
                    is Resource.Success -> {
                        if (weatherViewModel.shouldNavigate)
                            findNavController()
                                .navigate(R.id.action_city_search_fragment_to_weather_results_fragment)

                        weatherViewModel.shouldNavigate = false
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.weatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.adapters.WeatherAdapter
import com.example.weatherapp.databinding.FragmentCitySearchBinding
import com.example.weatherapp.databinding.FragmentWeatherResultsBinding
import com.example.weatherapp.model.HourlyWeather
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.network.ApiHelper
import com.example.weatherapp.network.RetrofitBuilder
import com.example.weatherapp.utils.Resource
import com.example.weatherapp.viewmodel.ViewModelFactory
import com.example.weatherapp.viewmodel.WeatherViewModel

class WeatherResultsFragment : Fragment() {

    private var _binding: FragmentWeatherResultsBinding? = null
    private val binding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by activityViewModels {
        ViewModelFactory(ApiHelper(RetrofitBuilder.weatherService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            weatherViewModel.weatherResponse.observe(viewLifecycleOwner) {
                rvWeather.apply {
                    adapter = WeatherAdapter(
                        (it as Resource.Success).data.list,
                        this@WeatherResultsFragment::onItemClicked
                    )
                    addItemDecoration(
                        DividerItemDecoration(
                            requireContext(),
                            RecyclerView.VERTICAL
                        )
                    )
                }
            }
        }
    }


    private fun onItemClicked(weather: HourlyWeather) {
        weatherViewModel.selectedWeather = weather
        findNavController().navigate(R.id.action_weather_results_fragment_to_weather_details_fragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
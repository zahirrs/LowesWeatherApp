package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCitySearchBinding
import com.example.weatherapp.databinding.WeatherItemBinding
import com.example.weatherapp.model.HourlyWeather
import com.example.weatherapp.model.Weather

class WeatherAdapter(
    private val weatherList: List<HourlyWeather>,
    private val listener: (weather: HourlyWeather) -> Unit
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WeatherViewHolder(
            WeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener { listener.invoke(weatherList[adapterPosition]) }
        }

    override fun getItemCount(): Int = weatherList.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    inner class WeatherViewHolder(private val binding: WeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherItem: HourlyWeather) = with(binding) {
            weatherDescription.text = weatherItem.weather[0].main
            temp.text = String.format(
                binding.root.context.getString(
                    R.string.weather_temp,
                    weatherItem.main?.temp?.toInt().toString()
                )
            )
        }

    }
}
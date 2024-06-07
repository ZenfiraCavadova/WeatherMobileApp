package com.zenfira_cavadova.home.weather_list

import android.health.connect.datatypes.units.Temperature
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.home.R
import com.zenfira_cavadova.home.databinding.WeatherContainerBinding
import kotlin.math.roundToInt

class WeatherAdapter(private val onItemClicked:(WeatherItem)->Unit, private var temperatureUnit: String, private var windSpeedUnit: String):ListAdapter<WeatherItem, WeatherAdapter.WeatherViewHolder>(WeatherDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view=WeatherContainerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item=getItem(position)
        holder.bindData(item,temperatureUnit, windSpeedUnit )
    }
    inner class WeatherViewHolder(private val  binding: WeatherContainerBinding): RecyclerView.ViewHolder(binding.root){
        fun bindData(item: WeatherItem, temperatureUnit:String, windSpeedUnit:String){
          binding.apply {
              temperature.text = when(temperatureUnit){
                  "K"-> item.temperature + " K"
                  "C"->convertKelvinToCelsius(item.temperature) + " °C"
                  "F"->convertKelvinToFahrenheit(item.temperature) + " °F"
                  else ->"N/A"
              }
              val highTempKelvin =item.highAndLowTemp.split(' ')[0]
              val lowTempKelvin =item.highAndLowTemp.split(' ')[1]
              val highTemp =when (temperatureUnit){
                  "K"-> highTempKelvin + " K"
                  "C"->convertKelvinToCelsius(highTempKelvin) + " °C"
                  "F"->convertKelvinToFahrenheit(highTempKelvin)+" °F"
                  else ->"N/A"
              }
              val lowTemp =when (temperatureUnit){
                  "K"-> lowTempKelvin + " K"
                  "C"->convertKelvinToCelsius(lowTempKelvin) + " °C"
                  "F"->convertKelvinToFahrenheit(lowTempKelvin)+" °F"
                  else ->"N/A"
              }
              highLowTemp.text = "$highTemp and $lowTemp"
              location.text = item.location
            binding.weatherIcon.setImageResource(getWeatherIconResource(item.weatherIcon))
              weatherDescription.text = item.weatherDescription
              root.setOnClickListener {
                  onItemClicked(item)
              }
          }

        }
    }

    fun updateUnits(newTempUnit:String, newWindSpeedUnit:String){
        temperatureUnit=newTempUnit
        windSpeedUnit=newWindSpeedUnit
        notifyDataSetChanged()
    }
    private fun convertKelvinToCelsius(temperatureInKelvin: String): String {
        // Conversion logic from Kelvin to Celsius: C = K - 273.15
        val temperatureInKelvinFloat = temperatureInKelvin.toFloatOrNull()
        return if (temperatureInKelvinFloat != null) {
            val temperatureInCelsius = temperatureInKelvinFloat - 273.15
            String.format("%.2f", temperatureInCelsius)
        } else {
            "N/A"
        }
    }

    private fun convertKelvinToFahrenheit(temperatureInKelvin: String): String {
        val temperatureInKelvinFloat = temperatureInKelvin.toFloatOrNull()
        return if (temperatureInKelvinFloat != null) {
            val temperatureInFahrenheit = (temperatureInKelvinFloat - 273.15) * 9/5 + 32
            String.format("%.2f", temperatureInFahrenheit)
        } else {
            "N/A"
        }
    }

    private fun convertMetersPerSecToKmPerHour(windSpeedInMetersPerSec: String): String {
        val windSpeedInMetersPerSecFloat = windSpeedInMetersPerSec.toFloatOrNull()
        return if (windSpeedInMetersPerSecFloat != null) {
            val windSpeedInKmPerHour = windSpeedInMetersPerSecFloat * 3.6
            String.format("%.2f", windSpeedInKmPerHour)
        } else {
            "N/A"
        }
    }

    private fun convertMetersPerSecToMilesPerHour(windSpeedInMetersPerSec: String): String {
        val windSpeedInMetersPerSecFloat = windSpeedInMetersPerSec.toFloatOrNull()
        return if (windSpeedInMetersPerSecFloat != null) {
            val windSpeedInMilesPerHour = windSpeedInMetersPerSecFloat * 2.237
            String.format("%.2f", windSpeedInMilesPerHour)
        } else {
            "N/A"
        }
    }

    private class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherItem>() {
        override fun areItemsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
            return oldItem.temperature == newItem.temperature &&
                    oldItem.highAndLowTemp == newItem.highAndLowTemp &&
                    oldItem.location == newItem.location &&
                    oldItem.weatherIcon == newItem.weatherIcon &&
                    oldItem.weatherDescription == newItem.weatherDescription
        }
    }

    fun getWeatherIconResource(iconCode:String):Int{
        return  when (iconCode){
            "01d"-> R.drawable.ic_clear
            "02d"->R.drawable.ic_few_clouds
            "03d"->R.drawable.ic_scattered_clouds
            "04d"->R.drawable.ic_broken_clouds
            "09d"->R.drawable.ic_shower_rain
            "10d"->R.drawable.ic_rain
            "11d"->R.drawable.ic_thunderstorm
            "13d"->R.drawable.ic_snow
            "50d"->R.drawable.ic_mist
            else-> R.drawable.ic_few_clouds
        }
    }
}
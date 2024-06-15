package com.zenfira_cavadova.home.weather_list

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.home.R
import com.zenfira_cavadova.home.databinding.WeatherContainerBinding

class WeatherAdapter(private val onItemClicked:(WeatherItem)->Unit, private var temperatureUnit: String, private var windSpeedUnit: String):ListAdapter<WeatherItem, WeatherAdapter.WeatherViewHolder>(WeatherDiffCallback()) {
    private var allWeatherItem: List<WeatherItem> = emptyList()
    init {
        allWeatherItem= mutableListOf()
    }

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
              val temp =convertTemp(item.temperature,temperatureUnit)
              temperature.text="$temp $temperatureUnit"
              val highTempKelvin =item.highAndLowTemp.split(' ')[0]
              Log.e("UNITTTTT","UNIT: $temp tempp  changingvggggggggg")
              val lowTempKelvin =item.highAndLowTemp.split(' ')[1]
              val highTemp=convertTemp(highTempKelvin,temperatureUnit)
              val lowTemp=convertTemp(lowTempKelvin,temperatureUnit)
              Log.e("AAAAAAAAAAAAAAAAAAAA","$highTemp $lowTemp $highTempKelvin $lowTempKelvin" )
              highLowTemp.text="$highTempKelvin $lowTempKelvin"
//              val highAndLowTempParts = item.highAndLowTemp.trim().split(' ')
//              if (highAndLowTempParts.size == 4 && highAndLowTempParts[0] == "H:" && highAndLowTempParts[2] == "L:") {
//                  val highTempKelvin = highAndLowTempParts[1]
//                  val lowTempKelvin = highAndLowTempParts[3]
//                  val highTemp = convertTemp(highTempKelvin, temperatureUnit)
//                  val lowTemp = convertTemp(lowTempKelvin, temperatureUnit)
//                  highLowTemp.text = "$highTemp $lowTemp"
//              } else {
//                  Log.e("WeatherAdapter", "Invalid highAndLowTemp format: ${item.highAndLowTemp}")
//              }

              location.text = item.location
              binding.weatherIcon.setImageResource(getWeatherIconResource(item.weatherIcon))
              weatherDescription.text = item.weatherDescription
              root.setOnClickListener {
                  onItemClicked(item)
              }
          }
        }
    }

    @SuppressLint("DefaultLocale")
    fun convertTemp(temp: String?, unit: String): String {
        Log.e("UNITTTTT","UNIT: $unit changingvggggggggg")
        return if (temp != null) {
            try {
                val tempValue = temp.toDouble()
                when (unit) {
                    "K" -> String.format("%.2f", tempValue)
                    "C" -> String.format("%.2f", tempValue - 273.15)
                    "F" -> String.format("%.2f", (tempValue - 273.15) * 9 / 5 + 32)
                    else -> "N/A"
                }
            } catch (e: NumberFormatException) {
                "N/A"
            }
        } else {
            "N/A"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUnits(tempUnit:String, windSpeedUnit:String){
        this.temperatureUnit=tempUnit
        this.windSpeedUnit=windSpeedUnit
        notifyDataSetChanged()
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
            "01d", "01n"-> R.drawable.ic_clear
            "02d", "02n"->R.drawable.ic_few_clouds
            "03d", "03n"->R.drawable.ic_scattered_clouds
            "04d", "04n"->R.drawable.ic_broken_clouds
            "09d"->R.drawable.ic_shower_rain
            "10d"->R.drawable.ic_rain
            "11d"->R.drawable.ic_thunderstorm
            "13d"->R.drawable.ic_snow
            "50d"->R.drawable.ic_mist
            else-> R.drawable.ic_few_clouds
        }
    }

    fun submitAll(weatherItems: List<WeatherItem>) {
        allWeatherItem = weatherItems
        submitList(allWeatherItem)
    }
    fun filterWeather(query: String) {
        val filteredNotes = allWeatherItem.filter {weatherItem ->
            weatherItem.location.contains(query, ignoreCase = true)
        }
        submitList(filteredNotes)
    }
}
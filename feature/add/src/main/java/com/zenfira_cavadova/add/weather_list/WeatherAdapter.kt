package com.zenfira_cavadova.add.weather_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zenfira_cavadova.add.R
import com.zenfira_cavadova.add.databinding.RecyclerWeatherViewBinding
import com.zenfira_cavadova.domain.entities.WeatherItem

class WeatherAdapter:ListAdapter<WeatherItem, WeatherAdapter.WeatherViewHolder>(WeatherDiffCallback()) {

    var weatherItemClickListener: (WeatherItem) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding= RecyclerWeatherViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item=getItem(position)
        Log.e("WeatherAdapter", "onBindViewHolder called with item: $item")
        Log.e("WeatherAdapter", "onBindViewHolder called for position: $position with item: $item")
        holder.bindData(item)

//        holder.itemView.findViewById<ImageButton>(R.id.btn_remove).setOnClickListener {
//        }
    }
    inner class WeatherViewHolder(private val  binding: RecyclerWeatherViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bindData(item: WeatherItem){
            Log.e("WeatherAdapter", "Binding data for location: ${item.location}")
            binding.temperature.text = "${item.temperature}°"
            binding.highLowTemp.text = "H:${item.highAndLowTemp.split(' ')[0]} L:${item.highAndLowTemp.split(' ')[1]}"
            binding.location.text = item.location
            binding.weatherIcon.setImageResource(getWeatherIconResource(item.weatherIcon))
            binding.weatherDescription.text = item.weatherDescription

            binding.btnRemove.setOnClickListener {
                weatherItemClickListener(item)
            }
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
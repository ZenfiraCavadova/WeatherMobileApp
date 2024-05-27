package com.zenfira_cavadova.add.weather_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zenfira_cavadova.add.databinding.WeatherContainerBinding
import com.zenfira_cavadova.domain.entities.WeatherItem

class WeatherAdapter:ListAdapter<WeatherItem, WeatherAdapter.WeatherViewHolder>(WeatherDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view= WeatherContainerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item=getItem(position)
        holder.bindData(item)
    }
    inner class WeatherViewHolder(private val  binding: WeatherContainerBinding): RecyclerView.ViewHolder(binding.root){
        fun bindData(item: WeatherItem){
            binding.temperature.text = item.temperature
            binding.highLowTemp.text = item.highAndLowTemp
            binding.location.text = item.location
            binding.weatherIcon.setImageResource(item.weatherIcon)
            binding.weatherDescription.text = item.weatherDescription
//            binding.date.text = formatDate(item.timestamp)
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
}
package com.zenfira_cavadova.add

import com.zenfira_cavadova.domain.entities.WeatherItem

interface WeatherItemClickListener  {
    fun onRemoveItemClick(weatherItem: WeatherItem)
}
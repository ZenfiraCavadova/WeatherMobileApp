package com.zenfira_cavadova.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.zenfira_cavadova.core.BaseFragment
import com.zenfira_cavadova.details.databinding.FragmentDetailsBinding
import com.zenfira_cavadova.domain.entities.WeatherItem
import kotlinx.coroutines.launch

class DetailsFragment : BaseFragment<FragmentDetailsBinding,DetailsViewModel,DetailsState,DetailsEffect,DetailsEvent>() {

    override fun getViewModelClass()=DetailsViewModel::class.java
    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding= {inflater,viewGroup, value ->
        FragmentDetailsBinding.inflate(inflater,viewGroup,value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        val weatherItem=arguments?.getParcelable<WeatherItem>("weatherItem")
//
//        weatherItem?.let {
//            viewModel.setWeatherItem(it)
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weatherItem.collect{ item ->
                item?.let {
                    binding.cityName.text =it.location
                    binding.temperature.text=it.temperature
                    binding.highLowTemp.text=it.highAndLowTemp
                    binding.weatherDescription.text=it.weatherDescription
                    binding.windSpeed.text="Wind Speed: ${it.windSpeed} "
                }
            }
        }
    }
}
package com.zenfira_cavadova.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.zenfira_cavadova.core.BaseFragment
import com.zenfira_cavadova.details.databinding.FragmentDetailsBinding
import kotlinx.coroutines.launch
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeDetailsFragment : BaseFragment<FragmentDetailsBinding,DetailsViewModel,DetailsState,DetailsEffect,DetailsEvent>() {
    private val args by navArgs<HomeDetailsFragmentArgs>()

    override fun getViewModelClass()=DetailsViewModel::class.java
    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding= {inflater,viewGroup, value ->
        FragmentDetailsBinding.inflate(inflater,viewGroup,value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val weatherItem=args.weatherItem
//            viewModel.setWeatherItem(weatherItem)

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
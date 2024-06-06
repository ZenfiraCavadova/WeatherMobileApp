package com.zenfira_cavadova.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.zenfira_cavadova.core.BaseFragment
import com.zenfira_cavadova.home.databinding.FragmentHomeBinding
import com.zenfira_cavadova.home.weather_list.WeatherAdapter
import com.zenfira_cavadova.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel,HomeState,HomeEffect, HomeEvent>(),WeatherUnitUpdateListener {

    private var adapter: WeatherAdapter? =null
    private lateinit var settingsViewModel: SettingsViewModel

    override fun getViewModelClass() =HomeViewModel::class.java
    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding ={inflater ,viewGroup, value ->
        FragmentHomeBinding.inflate(inflater,viewGroup,value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsViewModel = ViewModelProvider(requireActivity()).get(SettingsViewModel::class.java)
        adapter= WeatherAdapter(
            onItemClicked = {weatherItem ->
//                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment2(weatherItem)
//                findNavController().navigate(action)
            },
            temperatureUnit = "K",
            windSpeedUnit = "mph"
        )
        binding.searchInp.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()){
                  viewModel.fetchWeatherForCity(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        binding.weatherList.adapter=adapter


        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.windSpeedUnit.collect { unit ->
                adapter?.updateUnits(settingsViewModel.temperatureUnit.value, unit)
            }
        }
    }
    override fun updateUnits(temperatureUnit: String, windSpeedUnit: String) {
        adapter?.updateUnits(temperatureUnit, windSpeedUnit)
    }

    override fun onStateUpdate(state: HomeState) {
        adapter?.submitList(state.weatherItems)

    }
}
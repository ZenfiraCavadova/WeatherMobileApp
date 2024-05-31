package com.zenfira_cavadova.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zenfira_cavadova.core.BaseFragment
import com.zenfira_cavadova.home.databinding.FragmentHomeBinding
import com.zenfira_cavadova.home.weather_list.WeatherAdapter


class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel,HomeState,HomeEffect, HomeEvent>() {
    private lateinit var adapter: WeatherAdapter

    override fun getViewModelClass() =HomeViewModel::class.java
    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding ={inflater ,viewGroup, value ->
        FragmentHomeBinding.inflate(inflater,viewGroup,value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        adapter=WeatherAdapter()
        binding.weatherList.adapter=adapter
    }

    override fun onStateUpdate(state: HomeState) {
        state.weatherItems.let { list ->
            Log.e("HomeFragment", "State updated with weather items: $list")
            adapter.submitList(list)
        }
    }
}
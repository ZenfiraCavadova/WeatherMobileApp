package com.zenfira_cavadova.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zenfira_cavadova.core.BaseFragment
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.search.databinding.FragmentSearchBinding
import com.zenfira_cavadova.search.weather_list.WeatherAdapter
import kotlinx.coroutines.launch


class SearchFragment : BaseFragment<FragmentSearchBinding,SearchViewModel,SearchState,SearchEffect,SearchEvent>() {
    private lateinit var adapter: WeatherAdapter
    override fun getViewModelClass() =SearchViewModel::class.java
    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding ={inflater ,viewGroup, value ->
        FragmentSearchBinding.inflate(inflater,viewGroup,value)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter= WeatherAdapter()
        binding.weatherContainer.layoutManager=LinearLayoutManager(context)
        binding.weatherContainer.adapter=adapter

        binding.searchInp.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()){
                    viewModel.fetchWeather(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

//        viewLifecycleOwner.lifecycleScope.launch{
//            viewModel.weatherData.collect{weatherResponse ->
//                weatherResponse?.let {
//                    val weatherItem= listOf(
//                        WeatherItem(
//                            temperature = it.temperature,
//                            highAndLowTemp = it.highAndLowTemp,
//                            location = it.location,
//                            weatherIcon = it.weatherIcon,
//                            weatherDescription = it.weatherDescription,
//                            windSpeed = it.windSpeed
//                        )
//                    )
//                    adapter.submitList(weatherItem)
//                    }
//                }
//            }
//        }
//
//    override fun onStateUpdate(state: SearchState) {
//        state.weatherItems.let { list ->
//            adapter.submitList(list)
//        }
    }
}


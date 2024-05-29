package com.zenfira_cavadova.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.search.databinding.FragmentSearchBinding
import com.zenfira_cavadova.search.weather_list.WeatherAdapter
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel :SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter= WeatherAdapter()
        binding.weatherContainer.layoutManager=LinearLayoutManager(context)
        binding.weatherContainer.adapter=adapter

        binding.searchInp.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    viewModel.fetchWeather(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.weatherData.collect{weatherResponse ->
                weatherResponse?.let {
                    val weatherItems = listOf(
                        WeatherItem(
                            temperature = it.temperature,
                            highAndLowTemp = it.highAndLowTemp,
                            location = it.location,
                            weatherIcon = it.weatherIcon,
                            weatherDescription = it.weatherDescription
                        )
                    )
                    adapter.submitList(weatherItems)
                    }
                }
            }
        }
}


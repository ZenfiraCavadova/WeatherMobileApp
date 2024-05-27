package com.zenfira_cavadova.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.home.databinding.FragmentHomeBinding
import com.zenfira_cavadova.home.weather_list.WeatherAdapter


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var adapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter=WeatherAdapter()
        binding.weatherList.adapter=adapter
    }

}
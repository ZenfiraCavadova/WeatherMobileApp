package com.zenfira_cavadova.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zenfira_cavadova.add.databinding.FragmentAddAndRemoveBinding
import com.zenfira_cavadova.add.weather_list.WeatherAdapter


class AddAndRemoveFragment : Fragment() {

    private lateinit var binding: FragmentAddAndRemoveBinding
    val viewModel by viewModels<AddAndRemoveViewModel>()
    private lateinit var adapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddAndRemoveBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter=WeatherAdapter()
        binding.weatherList.adapter=adapter
    }

}
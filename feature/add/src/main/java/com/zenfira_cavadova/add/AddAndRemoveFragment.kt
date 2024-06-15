package com.zenfira_cavadova.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zenfira_cavadova.add.databinding.FragmentAddAndRemoveBinding
import com.zenfira_cavadova.add.weather_list.WeatherAdapter
import com.zenfira_cavadova.core.BaseFragment
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.settings.SettingsViewModel
import com.zenfira_cavadova.settings.WeatherUnitUpdateListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddAndRemoveFragment : BaseFragment<FragmentAddAndRemoveBinding, AddAndRemoveViewModel,AddState, AddEffect,AddEvent>(),WeatherItemClickListener,WeatherUnitUpdateListener {
    private var adapter: WeatherAdapter? =null
    private lateinit var settingsViewModel: SettingsViewModel


    override fun getViewModelClass()=AddAndRemoveViewModel::class.java

    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddAndRemoveBinding ={inflater, viewGroup, value ->
        FragmentAddAndRemoveBinding.inflate(inflater,viewGroup,value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsViewModel = ViewModelProvider(requireActivity()).get(SettingsViewModel::class.java)
        settingsViewModel.setFragmentListener(this)
        binding.weatherList.adapter=adapter
        binding.weatherList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.fabAddCity.setOnClickListener {
            showBottomSheetDialog()
        }
        lifecycleScope.launch {
            settingsViewModel.temperatureUnit.value.let { unit ->
                adapter = WeatherAdapter(
                    this@AddAndRemoveFragment,
                    temperatureUnit = unit
                )
                binding.weatherList.adapter = adapter
            }
        }
    }

    override fun updateUnits(temperatureUnit: String,windSpeedUnit:String) {
        adapter?.updateUnit(temperatureUnit)
    }
    override fun onRemoveItemClick(weatherItem: WeatherItem){
        viewModel.removeWeatherItem(weatherItem)
    }


    private fun showBottomSheetDialog(){
        CustomBottomSheetDialog().show(parentFragmentManager, CustomBottomSheetDialog::class.java.canonicalName)
    }

    override fun onStateUpdate(state: AddState) {
        state.weatherItems?.let { adapter?.submitAll(it) }
    }

}
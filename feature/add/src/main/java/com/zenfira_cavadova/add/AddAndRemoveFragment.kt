package com.zenfira_cavadova.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zenfira_cavadova.add.databinding.FragmentAddAndRemoveBinding
import com.zenfira_cavadova.add.weather_list.WeatherAdapter
import com.zenfira_cavadova.core.BaseFragment


class AddAndRemoveFragment : BaseFragment<FragmentAddAndRemoveBinding, AddAndRemoveViewModel,AddState, AddEffect,AddEvent>() {
    private lateinit var adapter: WeatherAdapter

    override fun getViewModelClass()=AddAndRemoveViewModel::class.java

    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddAndRemoveBinding ={inflater, viewGroup, value ->
        FragmentAddAndRemoveBinding.inflate(inflater,viewGroup,value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchAllWeatherItems()

        adapter=WeatherAdapter()
        Log.e("AddAndRemoveFragment", "RecyclerView $adapter set")

        binding.weatherList.adapter=adapter


        binding.fabAddCity.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog(){
        CustomBottomSheetDialog().show(parentFragmentManager, CustomBottomSheetDialog::class.java.canonicalName)
    }
    override fun onStateUpdate(state: AddState) {
        state.weatherItems.let { list ->
            Log.e("AddAndRemoveFragment", "State updated with weather items: $list")
            adapter.submitList(list)
        }
    }

}
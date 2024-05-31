package com.zenfira_cavadova.add

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.zenfira_cavadova.add.databinding.DialogCustomBottomSheetBinding

class CustomBottomSheetDialog:BottomSheetDialogFragment() {
    private lateinit var binding: DialogCustomBottomSheetBinding
    private val viewModel: AddAndRemoveViewModel by viewModels()
    private var selectedCityName:String? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DialogCustomBottomSheetBinding.inflate(inflater,container,false)
        fetchFavoriteCities()
        addCityButton()
        return binding.root
    }

    private fun fetchFavoriteCities(){
        val remoteConfig = Firebase.remoteConfig
        val configSettings= remoteConfigSettings {
            minimumFetchIntervalInSeconds =3600
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf("favorite_cities" to """["Baku", "London", "Seoul"]"""))

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val favoriteCitiesJson = remoteConfig.getString("favorite_cities")
                    val favoriteCities: List<String> = parseFavoriteCities(favoriteCitiesJson)
                    displayFavoriteCities(favoriteCities)
                }
            }
    }

    private fun parseFavoriteCities(json:String):List<String>{
        val jsonArray=org.json.JSONArray(json)
        return List(jsonArray.length()){jsonArray.getString(it)}
    }

    private fun displayFavoriteCities(cities:List<String>){
        val adapter=ArrayAdapter(requireContext(), R.layout.simple_list_item_1,cities)
        binding.favCities.adapter=adapter
        binding.favCities.onItemClickListener =AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedCityName=cities[position]
        }

    }

    private fun addCityButton(){
        binding.btnAddCity.setOnClickListener {
            val cityName= selectedCityName ?:binding.inputCityName.text.toString()
            if (cityName.isNotBlank()){
                viewModel.fetchWeatherForCity(cityName){weatherItem ->
                    if (weatherItem != null){
                        viewModel.addWeatherItem(weatherItem)
                        Toast.makeText(requireContext(), "$cityName added",
                            Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                    else{
                        Toast.makeText(requireContext(), "Failed to fetch weather data for $cityName",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(requireContext(), "Please enter a city name",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }


}
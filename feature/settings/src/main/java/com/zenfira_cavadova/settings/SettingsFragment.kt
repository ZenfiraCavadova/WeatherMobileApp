package com.zenfira_cavadova.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.zenfira_cavadova.core.BaseFragment
import com.zenfira_cavadova.settings.databinding.FragmentSettingsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale


class SettingsFragment : BaseFragment<FragmentSettingsBinding,SettingsViewModel,SettingsState,SettingsEffect,SettingsEvent>() {
    private lateinit var settingsViewModel:SettingsViewModel
    override fun getViewModelClass() =SettingsViewModel::class.java
    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingsBinding ={inflater ,viewGroup, value ->
        FragmentSettingsBinding.inflate(inflater,viewGroup,value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val masterKey = MasterKey.Builder(requireContext())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            requireContext(),
            SECRET_SHARED_PREF,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        settingsViewModel=SettingsViewModel.create(sharedPreferences)

        setupSpinners()
        setListeners()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            settingsViewModel.temperatureUnit.collect{unit ->
                val adapter =(binding.tempSpinner.adapter as? ArrayAdapter<CharSequence>)
                adapter?.let {
                    val position = it.getPosition(unit)
                    binding.tempSpinner.setSelection(position)
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main){
            settingsViewModel.windSpeedUnit.collect{unit ->
                val adapter =(binding.windSpinner.adapter as? ArrayAdapter<CharSequence>)
                adapter?.let {
                    val position = it.getPosition(unit)
                    binding.windSpinner.setSelection(position)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main){
            settingsViewModel.language.collect{language ->
                val adapter =(binding.langSpinner.adapter as? ArrayAdapter<CharSequence>)
                adapter?.let {
                    val position = it.getPosition(language)
                binding.langSpinner.setSelection(position)
                updateLocale(language)
                    }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            settingsViewModel.updateWeather.collect{update ->
                binding.switchWeather.isChecked= update
            }
        }

    }

    private fun setupSpinners() {
        ArrayAdapter.createFromResource(
            requireContext(), R.array.temperature_units, android.R.layout.simple_spinner_item
        ).also { adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.tempSpinner.adapter=adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(), R.array.wind_speed_units, android.R.layout.simple_spinner_item
        ).also { adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.windSpinner.adapter=adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(), R.array.languages, android.R.layout.simple_spinner_item
        ).also { adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.langSpinner.adapter=adapter
        }
    }

    private fun setListeners() {
        binding.tempSpinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedUnit= parent?.getItemAtPosition(position).toString()
                settingsViewModel.setTemperatureUnit(selectedUnit)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.windSpinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedUnit= parent?.getItemAtPosition(position).toString()
                settingsViewModel.setWindSpeedUnit(selectedUnit)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.langSpinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLang= parent?.getItemAtPosition(position).toString()
                settingsViewModel.setLanguage(selectedLang)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.switchWeather.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setUpdateWeather(isChecked)
        }

    }
    private fun updateLocale(language:String){
        val langCode= when (language){
            "Eng"->"en"
            "Aze"->"az"
            else ->"en"
        }
        if (LocaleHelper.getLocale(requireContext())!= langCode){
            LocaleHelper.setLocale(requireContext(),langCode)
            val configuration =resources.configuration
            configuration.setLocale(Locale(langCode))
            resources.updateConfiguration(configuration, resources.displayMetrics)
            Log.e("SettingsFragment", "Locale updated to $langCode")
        }
    }


    companion object{
        private const val SECRET_SHARED_PREF = "secret_shared_prefs"
    }
}
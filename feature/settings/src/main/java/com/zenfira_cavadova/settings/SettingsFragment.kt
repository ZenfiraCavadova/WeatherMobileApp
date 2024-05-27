package com.zenfira_cavadova.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.zenfira_cavadova.settings.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
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

        settingsViewModel=SettingsViewModel(sharedPreferences)

        settingsViewModel.temperatureUnit.observe(viewLifecycleOwner){unit ->
            val position =(binding.tempSpinner.adapter as ArrayAdapter<CharSequence>).getPosition(unit)
            binding.tempSpinner.setSelection(position)
        }

        settingsViewModel.windSpeedUnit.observe(viewLifecycleOwner){unit ->
            val position =(binding.windSpinner.adapter as ArrayAdapter<CharSequence>).getPosition(unit)
            binding.windSpinner.setSelection(position)
        }

        settingsViewModel.language.observe(viewLifecycleOwner){language ->
            val position =(binding.langSpinner.adapter as ArrayAdapter<CharSequence>).getPosition(language)
            binding.langSpinner.setSelection(position)
        }
        settingsViewModel.updateWeather.observe(viewLifecycleOwner){update ->
            binding.switchWeather.isChecked= update
        }

        setupSpinners()
        setListeners()

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
                settingsViewModel.setTemperatureUnit(selectedUnit)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.langSpinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLang= parent?.getItemAtPosition(position).toString()
                settingsViewModel.setTemperatureUnit(selectedLang)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.switchWeather.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setUpdateWeather(isChecked)
        }

    }

    companion object{
        private const val SECRET_SHARED_PREF = "secret_shared_prefs"
    }
}
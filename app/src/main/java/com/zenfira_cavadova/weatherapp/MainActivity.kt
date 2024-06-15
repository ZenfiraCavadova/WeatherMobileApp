package com.zenfira_cavadova.weatherapp

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.zenfira_cavadova.data.database.db.DatabaseManager
import com.zenfira_cavadova.settings.LocaleHelper
import com.zenfira_cavadova.weatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        isGranted
    }


    override fun attachBaseContext(newBase: Context?) {
        val sharedPreferences=newBase?.getSharedPreferences("secret_shared_prefs",Context.MODE_PRIVATE)
        val language= sharedPreferences?.getString("language","en")
        val context= newBase?.let { LocaleHelper.setLocale(it, language ?:"en") }
        super.attachBaseContext(context ?:newBase)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        askNotificationPermission()
        DatabaseManager.initDatabase(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleHelper.updateResources(this,LocaleHelper.getLocale(this))
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_nav_graph,
                R.id.add_nav_graph,
                R.id.settings_nav_graph
            )
        )
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_nav_graph -> {
                }
                R.id.add_nav_graph -> {
                }
                R.id.settings_nav_graph -> {
                }
            }
        }
    }
}
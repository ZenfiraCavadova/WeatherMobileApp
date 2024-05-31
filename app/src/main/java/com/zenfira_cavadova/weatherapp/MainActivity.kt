package com.zenfira_cavadova.weatherapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.zenfira_cavadova.data.database.db.DatabaseManager
import com.zenfira_cavadova.settings.LocaleHelper
import com.zenfira_cavadova.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        DatabaseManager.initDatabase(this)
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
//        val toolbar = findViewById<Toolbar>(R.id.app_bar)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)

//        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_nav_graph -> {
//                    toolbar.findViewById<TextView>(R.id.toolbar_title)?.text = "Notes"
//                    toolbar.findViewById<TextView>(R.id.toolbar_subtitle)?.text = "22 December, 2021"
//                    binding.searchIcon.visibility = View.VISIBLE
                }
                R.id.add_nav_graph -> {
//                    toolbar.findViewById<TextView>(R.id.toolbar_title)?.text = ""
//                    toolbar.findViewById<TextView>(R.id.toolbar_subtitle)?.text = ""
//                    binding.searchIcon.visibility = View.VISIBLE
//                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
//                    toolbar.setNavigationOnClickListener {
//                        onBackPressedDispatcher.onBackPressed()
//                    }
                }
                R.id.settings_nav_graph -> {
//                    toolbar.findViewById<TextView>(R.id.toolbar_title)?.text = "Notes"
//                    toolbar.findViewById<TextView>(R.id.toolbar_subtitle)?.text = ""
//                    binding.searchIcon.visibility = View.GONE
                }
            }
//            binding.searchIcon.setOnClickListener {
//                navController.navigate(com.zenfira_cavadova.search.R.id.search_nav_graph)
//            }
        }
    }
}
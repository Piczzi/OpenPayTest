package com.example.openpaytest.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.openpaytest.R
import com.example.openpaytest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> {
                    navController.navigate(R.id.nav_profile)
                    true
                }

                R.id.nav_movies -> {
                    navController.navigate(R.id.nav_movies)
                    true
                }

                R.id.nav_map -> {
                    navController.navigate(R.id.nav_map)
                    true
                }

                else -> false
            }
        }

        binding.navView.selectedItemId = R.id.nav_movies
    }

}
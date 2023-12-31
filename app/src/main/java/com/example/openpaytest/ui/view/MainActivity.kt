package com.example.openpaytest.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.openpaytest.R
import com.example.openpaytest.R.string
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
                    binding.mtGreetingLabel.text = getString(string.toolbar_title_profile)
                    navController.navigate(R.id.nav_profile)
                    true
                }

                R.id.nav_movies -> {
                    binding.mtGreetingLabel.text = getString(string.toolbar_title_movies)
                    navController.navigate(R.id.nav_movies)
                    true
                }

                R.id.nav_map -> {
                    binding.mtGreetingLabel.text = getString(string.toolbar_title_map)
                    navController.navigate(R.id.nav_map)
                    true
                }

                R.id.nav_pictures -> {
                    binding.mtGreetingLabel.text = getString(string.toolbar_title_pictures)
                    navController.navigate(R.id.nav_pictures)
                    true
                }

                else -> false
            }
        }

        binding.navView.selectedItemId = R.id.nav_movies

        setUpObservers()
    }

    private fun setUpObservers() {
        mainViewModel.isLoading.observe(this@MainActivity) { isLoading ->
            binding.lottieLoading.isVisible = isLoading
            if (isLoading) {
                binding.lottieLoading.playAnimation()
            } else {
                binding.lottieLoading.pauseAnimation()
            }
        }
    }

}
package com.example.movieapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityCineverseBinding
import com.example.movieapp.presentation.fragment.CineverseHomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CineverseActivity : BaseActivity() {

    private lateinit var binding: ActivityCineverseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCineverseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bottomNavigation()

    }

    private fun bottomNavigation() {
        val navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        binding.chipNavigationBar.setOnItemSelectedListener { id ->
            when (id) {
                R.id.cineverseTicketFragment -> navController.navigate(R.id.cineverseTicketFragment)
                R.id.cineverseHomeFragment -> navController.navigate(R.id.cineverseHomeFragment)
                R.id.cineverseSearchFragment -> navController.navigate(R.id.cineverseSearchFragment)
                R.id.cineverseAccountFragment -> navController.navigate(R.id.cineverseAccountFragment)
                R.id.movie -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

}
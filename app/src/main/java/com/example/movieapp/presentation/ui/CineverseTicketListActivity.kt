package com.example.movieapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityCineverseTicketListBinding
import com.example.movieapp.presentation.adapter.CineverseExpiredTicketsAdapter
import com.example.movieapp.presentation.adapter.CineverseUpcomingTicketsAdapter
import com.example.movieapp.presentation.viewmodel.TicketListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CineverseTicketListActivity : BaseActivity() {

    private lateinit var binding: ActivityCineverseTicketListBinding
    private val viewModel: TicketListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCineverseTicketListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bottomNavigation()
        initUpcomingTickets()
        initExpiredTickets()
    }

    private fun initExpiredTickets() {
        viewModel.expiredTickets.observe(this) { expiredTickets ->
            if (!expiredTickets.isNullOrEmpty()) {
                binding.expiredTicketsView.layoutManager = LinearLayoutManager(this@CineverseTicketListActivity, LinearLayoutManager.VERTICAL, false)
                binding.expiredTicketsView.adapter = CineverseExpiredTicketsAdapter ( expiredTickets)
            }
        }

        viewModel.expiredTickets()
    }

    private fun initUpcomingTickets() {
        binding.upcomingTicketsProgressBar.visibility = View.VISIBLE
        viewModel.upcomingTickets.observe(this) { upcomingTickets ->
            binding.upcomingTicketsProgressBar.visibility = View.GONE

            if (!upcomingTickets.isNullOrEmpty()) {
                binding.upcomingTicketsView.visibility = View.VISIBLE
                binding.upcomingTicketsView.layoutManager = LinearLayoutManager(this@CineverseTicketListActivity, LinearLayoutManager.VERTICAL, false)
                binding.upcomingTicketsView.adapter = CineverseUpcomingTicketsAdapter (upcomingTickets)
            } else {
                binding.upcomingTicketsView.visibility = View.GONE
            }
        }
        viewModel.upcomingTickets()
    }

    private fun bottomNavigation() {
        binding.chipNavigationBar.setOnItemSelectedListener { id ->
            when(id) {
                R.id.cineverseTicketFragment -> {
                    val intent = Intent (this, CinemaTicketDetailActivity::class.java)
                    startActivity(intent) }
                R.id.cineverseHomeFragment -> {
                    val intent = Intent (this, CineverseActivity::class.java)
                    startActivity(intent) }
                R.id.movie -> {
                    val intent = Intent ( this, MainActivity::class.java)
                    startActivity(intent) }
                R.id.cineverseSearchFragment -> {

                }
                else  -> {

                }
            }
        }
    }


}
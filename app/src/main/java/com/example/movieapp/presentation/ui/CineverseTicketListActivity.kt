package com.example.movieapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityCineverseTicketListBinding
import com.example.movieapp.domain.model.TicketModel
import com.example.movieapp.presentation.adapter.CineverseTicketListAdapter
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
        initTicketList()
    }

    private fun initTicketList() {
        binding.ticketListProgressBar.visibility = View.VISIBLE
        viewModel.ticketlist.observe(this) { ticketlist ->
            binding.ticketListProgressBar.visibility = View.GONE

            if (!ticketlist.isNullOrEmpty()) {
                binding.ticketListView.visibility = View.VISIBLE
                binding.ticketListView.layoutManager = LinearLayoutManager(this@CineverseTicketListActivity, LinearLayoutManager.VERTICAL, false)
                binding.ticketListView.adapter = CineverseTicketListAdapter (ticketlist)
            } else {
                binding.ticketListView.visibility = View.GONE
            }
        }
        viewModel.loadTicketList()
    }

    private fun bottomNavigation() {
        binding.chipNavigationBar.setOnItemSelectedListener { id ->
            when(id) {
                R.id.ticket -> {
                    val intent = Intent (this, CinemaTicketDetailActivity::class.java)
                    startActivity(intent) }
                R.id.home -> {
                    val intent = Intent (this, CineverseActivity::class.java)
                    startActivity(intent) }
                R.id.movie -> {
                    val intent = Intent ( this, MainActivity::class.java)
                    startActivity(intent) }
                R.id.search -> {

                }
                else  -> {

                }
            }
        }
    }


}
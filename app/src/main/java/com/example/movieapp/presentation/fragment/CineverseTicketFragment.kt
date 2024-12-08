package com.example.movieapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentCineverseTicketBinding
import com.example.movieapp.presentation.adapter.CineverseExpiredTicketsAdapter
import com.example.movieapp.presentation.adapter.CineverseUpcomingTicketsAdapter
import com.example.movieapp.presentation.viewmodel.TicketListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CineverseTicketFragment : Fragment() {

    private lateinit var binding: FragmentCineverseTicketBinding
    private val viewModel: TicketListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCineverseTicketBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUpcomingTickets()
        initExpiredTickets()
    }

    private fun initExpiredTickets() {
        viewModel.expiredTickets.observe(viewLifecycleOwner) { expiredTickets ->
            if (!expiredTickets.isNullOrEmpty()) {
                binding.expiredTicketsView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.expiredTicketsView.adapter = CineverseExpiredTicketsAdapter ( expiredTickets)
            }
        }

        viewModel.expiredTickets()
    }

    private fun initUpcomingTickets() {
        binding.upcomingTicketsProgressBar.visibility = View.VISIBLE
        viewModel.upcomingTickets.observe(viewLifecycleOwner) { upcomingTickets ->
            binding.upcomingTicketsProgressBar.visibility = View.GONE

            if (!upcomingTickets.isNullOrEmpty()) {
                binding.upcomingTicketsView.visibility = View.VISIBLE
                binding.upcomingTicketsView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.upcomingTicketsView.adapter = CineverseUpcomingTicketsAdapter (upcomingTickets)
            } else {
                binding.upcomingTicketsView.visibility = View.GONE
            }
        }
        viewModel.upcomingTickets()
    }

}
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
import com.example.movieapp.databinding.FragmentCineverseSearchBinding
import com.example.movieapp.databinding.FragmentCineverseTicketBinding
import com.example.movieapp.presentation.adapter.CineverseExpiredTicketsAdapter
import com.example.movieapp.presentation.adapter.CineverseUpcomingTicketsAdapter
import com.example.movieapp.presentation.viewmodel.TicketListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CineverseSearchFragment : Fragment() {

    private lateinit var binding: FragmentCineverseSearchBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCineverseSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}
package com.example.movieapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.databinding.FragmentCineverseSearchBinding
import com.example.movieapp.presentation.adapter.CineverseSearchAdapter
import com.example.movieapp.presentation.viewmodel.CineverseSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CineverseSearchFragment : Fragment() {

    private lateinit var binding: FragmentCineverseSearchBinding
    private val viewModel: CineverseSearchViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCineverseSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        observeViewModel()

        viewModel.searchMovies()

    }


    private fun setupUI() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.searchTxt.addTextChangedListener() { text ->
            val query = text.toString().trim()
            viewModel.searchMovies(query)
        }
    }

    private fun observeViewModel() {
        viewModel.searchResult.observe(viewLifecycleOwner) { searchResults ->
            binding.progressBar2.visibility = View.GONE

            if ( !searchResults.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.VISIBLE
                binding.recyclerView.adapter = CineverseSearchAdapter(searchResults)
            } else {
                binding.recyclerView.visibility = View.GONE

                binding.searchTxt.error = "No movies found for your search."
            }
        }
    }


}
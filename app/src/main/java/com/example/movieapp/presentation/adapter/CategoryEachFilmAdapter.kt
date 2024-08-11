package com.example.movieapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ViewholderCategoryBinding

class CategoryEachFilmAdapter(val topMovies: ArrayList<String>) : RecyclerView.Adapter<CategoryEachFilmAdapter.CategoryEachFilmViewholder>() {
    private var context : android.content.Context? = null

    class CategoryEachFilmViewholder(val binding : ViewholderCategoryBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryEachFilmViewholder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryEachFilmViewholder(binding)
    }

    override fun getItemCount(): Int = topMovies.size

    override fun onBindViewHolder(holder: CategoryEachFilmViewholder, position: Int) {
        holder.binding.titleTxt.text = topMovies[position]

    }
}
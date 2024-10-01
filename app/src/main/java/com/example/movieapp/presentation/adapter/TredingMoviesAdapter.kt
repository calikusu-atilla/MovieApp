package com.example.movieapp.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.TredingViewholderBinding
import com.example.movieapp.domain.model.TrendingMovieModel
import com.example.movieapp.presentation.ui.DetailActivity
import com.example.movieapp.util.downloadFromUrl
import com.example.movieapp.util.placeholderProgressBar


class TredingMoviesAdapter(val tredingMovies: List<TrendingMovieModel>):RecyclerView.Adapter<TredingMoviesAdapter.TredingViewHolder>(){

    private var context : android.content.Context? = null

    class TredingViewHolder(val binding: TredingViewholderBinding):RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TredingViewHolder {
        context = parent.context
        val binding = TredingViewholderBinding.inflate(LayoutInflater.from(context),parent,false)
        return TredingViewHolder(binding)

    }

    override fun getItemCount(): Int = tredingMovies.size

    override fun onBindViewHolder(holder: TredingViewHolder, position: Int) {
        holder.binding.nameTxt.text = tredingMovies[position].title
        holder.binding.pic.downloadFromUrl("https://image.tmdb.org/t/p/w500${tredingMovies[position].posterPath}", placeholderProgressBar(context!!))

        /*
        val fullPosterUrl = "https://image.tmdb.org/t/p/w500${upcomingMovies[position].posterPath}"
        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(30))
         Glide.with(holder.itemView.context)
            .load(fullPosterUrl)
            .apply(requestOptions)
            .into(holder.binding.pic)

         */

        holder.itemView.setOnClickListener {
            val intent = Intent (holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("movieId",tredingMovies[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }
}
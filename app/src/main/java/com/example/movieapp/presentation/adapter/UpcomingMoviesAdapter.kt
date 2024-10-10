package com.example.movieapp.presentation.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.UpcomingmoviesViewholderBinding
import com.example.movieapp.domain.model.UpcomingMoviesModel
import com.example.movieapp.presentation.ui.DetailActivity
import com.example.movieapp.util.downloadFromUrl
import com.example.movieapp.util.placeholderProgressBar

class UpcomingMoviesAdapter(val upcomingMovies: List<UpcomingMoviesModel>):RecyclerView.Adapter<UpcomingMoviesAdapter.UpcomingMoviesViewHolder>() {

    private var context: android.content.Context? = null

    class UpcomingMoviesViewHolder(val binding: UpcomingmoviesViewholderBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMoviesViewHolder {
        context = parent.context
        val binding = UpcomingmoviesViewholderBinding.inflate(LayoutInflater.from(context), parent, false)
        return UpcomingMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingMoviesViewHolder, position: Int) {

        holder.binding.nameTxt.text = upcomingMovies[position].title
        holder.binding.pic.downloadFromUrl("https://image.tmdb.org/t/p/w500${upcomingMovies[position].posterPath}", placeholderProgressBar(context!!))

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
            intent.putExtra("movieId",upcomingMovies[position].id)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = upcomingMovies.size

}

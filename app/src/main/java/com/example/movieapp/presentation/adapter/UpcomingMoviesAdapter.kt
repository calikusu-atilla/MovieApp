package com.example.movieapp.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.UpcomingmoviesViewholderBinding
import com.example.movieapp.domain.model.UpcomingMoviesModel
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
        //holder.binding.pic.downloadFromUrl(upcomingMovies[position].posterPath, placeholderProgressBar(context!!))



        val fullPosterUrl = "https://image.tmdb.org/t/p/w500${upcomingMovies[position].posterPath}"
        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(30))
         Glide.with(holder.itemView.context)
            .load(fullPosterUrl)
            .apply(requestOptions)
            .into(holder.binding.pic)




    }

    override fun getItemCount(): Int = upcomingMovies.size

}

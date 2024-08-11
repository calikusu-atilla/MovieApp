package com.example.movieapp.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.TopmoviesViewholderBinding
import com.example.movieapp.domain.model.TopMoviesModel
import com.example.movieapp.presentation.ui.DetailActivity

class TopMoviesAdapter(val topMovies: List<TopMoviesModel>):RecyclerView.Adapter<TopMoviesAdapter.TopMoviesViewholder>() {
    private var context : android.content.Context? = null

    class TopMoviesViewholder(val binding: TopmoviesViewholderBinding):RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMoviesViewholder {
        context = parent.context
        val binding = TopmoviesViewholderBinding.inflate(LayoutInflater.from(context),parent,false)
        return TopMoviesViewholder(binding)
    }

    override fun getItemCount(): Int = topMovies.size

    override fun onBindViewHolder(holder: TopMoviesViewholder, position: Int) {
        holder.binding.nameTxt.text = topMovies[position].title

        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(30))
        Glide.with(holder.itemView.context)
            .load(topMovies[position].poster)
            .apply(requestOptions)
            .into(holder.binding.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent (holder.itemView.context,DetailActivity::class.java)
            intent.putExtra("object",topMovies[position])
            holder.itemView.context.startActivity(intent)
        }

    }
}
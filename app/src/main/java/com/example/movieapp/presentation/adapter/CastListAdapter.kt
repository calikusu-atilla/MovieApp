package com.example.movieapp.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ViewholderCastBinding
import com.example.movieapp.domain.model.CastModel
import com.example.movieapp.domain.model.TmdbApiCastModel
import com.example.movieapp.domain.model.TopMoviesModel


class CastListAdapter(val cast: List<TmdbApiCastModel>) : RecyclerView.Adapter<CastListAdapter.CastListViewholder>() {

    private var context : android.content.Context? = null

    class CastListViewholder(val binding : ViewholderCastBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastListViewholder {
        context = parent.context
        val binding = ViewholderCastBinding.inflate(LayoutInflater.from(context),parent,false)
        return CastListViewholder(binding)
    }

    override fun getItemCount(): Int = cast.size

    override fun onBindViewHolder(holder: CastListViewholder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(cast[position].PicUrl)
            .into(holder.binding.pic)

        holder.binding.nameTxt.text = cast[position].Aktor

    }
}
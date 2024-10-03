package com.example.movieapp.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R
import com.example.movieapp.databinding.ViewholderCastBinding
import com.example.movieapp.domain.model.TmdbApiCastModel


class CastListAdapter(val cast: List<TmdbApiCastModel?>) : RecyclerView.Adapter<CastListAdapter.CastListViewholder>() {

    private var context: android.content.Context? = null
    private val filteredCast: List<TmdbApiCastModel> = cast.filterNotNull().filter { !it.PicUrl.isNullOrEmpty() }

    class CastListViewholder(val binding: ViewholderCastBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastListViewholder {
        context = parent.context
        val binding = ViewholderCastBinding.inflate(LayoutInflater.from(context), parent, false)
        return CastListViewholder(binding)
    }

    override fun getItemCount(): Int = filteredCast.size

    override fun onBindViewHolder(holder: CastListViewholder, position: Int) {

        val currentCast = filteredCast[position]

        val requestOptions = RequestOptions().transform(CenterCrop())
            Glide.with(context!!)
                .load("https://image.tmdb.org/t/p/w500${currentCast.PicUrl}")
                .apply(requestOptions)
                .into(holder.binding.pic)

            holder.binding.nameTxt.text = currentCast.Aktor ?: "Unknown"
        }
    }

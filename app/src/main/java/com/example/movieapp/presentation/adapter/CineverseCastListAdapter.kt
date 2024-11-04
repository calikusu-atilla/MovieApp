package com.example.movieapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.ViewholderCineverseCastBinding
import com.example.movieapp.domain.model.CineverseCastModel

class CineverseCastListAdapter(private var castItems: ArrayList<CineverseCastModel>) : RecyclerView.Adapter<CineverseCastListAdapter.CineverseCastViewholder>() {

    private var context: android.content.Context? = null

    class CineverseCastViewholder(val binding: ViewholderCineverseCastBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseCastViewholder {
        context = parent.context
        val binding = ViewholderCineverseCastBinding.inflate(LayoutInflater.from(context), parent, false)
        return CineverseCastViewholder(binding)
    }

    override fun getItemCount(): Int = castItems.size

    override fun onBindViewHolder(holder: CineverseCastViewholder, position: Int) {
        val castItem = castItems[position]

        if (castItem != null) {
            val requestOptions = RequestOptions().transform(CenterCrop())
            Glide.with(context!!)
                .load(castItem.PicUrl)
                .apply(requestOptions)
                .into(holder.binding.picCast)

            holder.binding.actorTxt.text = castItem.Actor
        }
    }
}
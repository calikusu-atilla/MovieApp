package com.example.movieapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.CineverseRecommendedViewholderBinding
import com.example.movieapp.domain.model.CineverseRecommendedModel

class CineverseRecommendedAdapter(private var recommendedItems: ArrayList<CineverseRecommendedModel>): RecyclerView.Adapter<CineverseRecommendedAdapter.CineverseRecommendedViewholder>() {

    private var context: android.content.Context ?= null

    class CineverseRecommendedViewholder(val binding: CineverseRecommendedViewholderBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseRecommendedViewholder {
        context = parent.context
        val binding = CineverseRecommendedViewholderBinding.inflate(LayoutInflater.from(context),parent,false)
        return CineverseRecommendedViewholder(binding)
    }

    override fun getItemCount(): Int = recommendedItems.size

    override fun onBindViewHolder(holder: CineverseRecommendedViewholder, position: Int) {
        val recommendedItem = recommendedItems[position]

        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(40))

        Glide.with(context!!)
            .load(recommendedItem.picUrl)
            .apply(requestOptions)
            .into(holder.binding.pic)

        holder.binding.nameTxt.text = recommendedItem.name


    }
}
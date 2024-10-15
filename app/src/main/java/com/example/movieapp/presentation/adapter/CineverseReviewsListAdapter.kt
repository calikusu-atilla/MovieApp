package com.example.movieapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.CineverseReviewsViewholderBinding
import com.example.movieapp.domain.model.CineverseReviewsModel

class CineverseReviewsListAdapter(private var reviewitems : List<CineverseReviewsModel?>):RecyclerView.Adapter<CineverseReviewsListAdapter.CineverseReviewsViewholder>() {

    private var context: android.content.Context ?= null


    class CineverseReviewsViewholder(val binding: CineverseReviewsViewholderBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseReviewsViewholder {
        context = parent.context
        val binding = CineverseReviewsViewholderBinding.inflate(LayoutInflater.from(context),parent,false)
        return CineverseReviewsViewholder(binding)
    }

    override fun getItemCount(): Int = reviewitems.size

    override fun onBindViewHolder(holder: CineverseReviewsViewholder, position: Int) {

        val reviewitem = reviewitems[position]

        reviewitem?.let {

            holder.binding.usernameTxt.text = reviewitem.username
            holder.binding.userScoreTxt.text = reviewitem.userscore
            holder.binding.reviewsTxt.text = reviewitem.review

        }



    }
}
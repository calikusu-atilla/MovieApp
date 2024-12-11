package com.example.movieapp.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.CineverseSearchViewholderBinding
import com.example.movieapp.domain.model.CineverseModel
import com.example.movieapp.presentation.ui.CineverseDetailActivity
import com.example.movieapp.util.downloadFromUrl
import com.example.movieapp.util.placeholderProgressBar

class CineverseSearchAdapter(private val items: List<CineverseModel>): RecyclerView.Adapter<CineverseSearchAdapter.CineverseSearchViewholder>()   {

        private var context: android.content.Context ?= null

    class CineverseSearchViewholder(val binding: CineverseSearchViewholderBinding): RecyclerView.ViewHolder(binding.root)  {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseSearchViewholder {
        context = parent.context
        val binding = CineverseSearchViewholderBinding.inflate(LayoutInflater.from(context),parent,false)
        return CineverseSearchViewholder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CineverseSearchViewholder, position: Int) {

        holder.binding.nameTxt.text = items[position].name
        holder.binding.pic.downloadFromUrl(items[position].image, placeholderProgressBar(context!!))

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CineverseDetailActivity::class.java)
            intent.putExtra("movieItem", items[position])
            holder.itemView.context.startActivity(intent)
        }
    }
}
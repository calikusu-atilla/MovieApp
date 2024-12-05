package com.example.movieapp.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.CineverseExpiredTicketViewholderBinding
import com.example.movieapp.databinding.CineverseUpcomingTicketViewholderBinding
import com.example.movieapp.domain.model.TicketModel
import com.example.movieapp.presentation.ui.CinemaTicketDetailActivity

class CineverseExpiredTicketsAdapter(private val ticketlist: List<TicketModel>): RecyclerView.Adapter<CineverseExpiredTicketsAdapter.CineverseExpiredTicketViewholder>() {

    private var context : android.content.Context ?= null

    class CineverseExpiredTicketViewholder(val binding: CineverseExpiredTicketViewholderBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseExpiredTicketViewholder {
        context= parent.context
        val binding = CineverseExpiredTicketViewholderBinding.inflate(LayoutInflater.from(context),parent,false)
        return CineverseExpiredTicketViewholder(binding)
    }

    override fun onBindViewHolder(holder: CineverseExpiredTicketViewholder, position: Int) {
        val ticketItem = ticketlist[position]

        if (ticketItem != null) {
            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(20))
            Glide.with(context!!)
                .load(ticketItem.picUrl)
                .apply(requestOptions)
                .into(holder.binding.picUrl)

            holder.binding.apply {
                dateTxt.text = ticketItem.date
                timeTxt.text = ticketItem.time
                movieNameTxt.text = ticketItem.movieName
                screenTxt.text = ticketItem.movieScreen
                totalPriceTxt.text = "$ ${ticketItem.totalPrice}"
            }

            holder.itemView.setOnClickListener {
                val intent = Intent (holder.itemView.context, CinemaTicketDetailActivity::class.java)
                intent.putExtra("ticketObject",ticketItem)
                holder.itemView.context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int = ticketlist.size
}
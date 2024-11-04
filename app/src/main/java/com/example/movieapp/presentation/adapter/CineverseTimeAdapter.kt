package com.example.movieapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.CineverseTimeViewholderBinding

class CineverseTimeAdapter(private val timeSlots: List<String>, private val timeClickListener: (String) -> Unit):RecyclerView.Adapter<CineverseTimeAdapter.CineverseTimeViewholder>(){

    private var selectedPosition = -1
    private var lastselectedPosition = -1

    inner class CineverseTimeViewholder(val binding: CineverseTimeViewholderBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind (time: String) {
            binding.timeTxt.text = time
            if (selectedPosition == position ){
                binding.timeTxt.setBackgroundResource(R.drawable.time_and_date_backgraund)
                binding.timeTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.black_overlay))
            }else{
                binding.timeTxt.setBackgroundResource(R.drawable.time_and_date_background2)
                binding.timeTxt.setTextColor(ContextCompat.getColor(itemView.context,R.color.white))
            }

            binding.root.setOnClickListener {
                val position = position
                if ( position != RecyclerView.NO_POSITION){
                    lastselectedPosition = selectedPosition
                    selectedPosition = position

                    timeClickListener(time)
                    notifyItemChanged(lastselectedPosition)
                    notifyItemChanged(selectedPosition)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseTimeViewholder {
        return CineverseTimeViewholder(CineverseTimeViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = timeSlots.size

    override fun onBindViewHolder(holder: CineverseTimeViewholder, position: Int) {
        holder.bind(timeSlots[position])
    }


}
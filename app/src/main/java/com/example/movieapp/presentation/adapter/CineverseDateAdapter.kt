package com.example.movieapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.CineverseDateViewholderBinding

class CineverseDateAdapter(private val timeSlots: List<String>, private val dateClickListener: (String)-> Unit):RecyclerView.Adapter<CineverseDateAdapter.CineverseDateViewholder>()  {

    private var selectedPosition = -1
    private var lastselectedPosition = -1

    inner class CineverseDateViewholder(val binding: CineverseDateViewholderBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind (date: String) {
           val dateParts = date.split("/")
            if (dateParts.size == 4) {
                binding.dayTxt.text = dateParts[0] //+ dateParts[1]
                binding.datMonthTxt.text = "${dateParts[1]}/${dateParts[2]}"
                binding.yearTxt.text = dateParts[3]

                if (selectedPosition == position ){
                    binding.mailLayout.setBackgroundResource(R.drawable.time_and_date_backgraund)
                    binding.dayTxt.setTextColor(ContextCompat.getColor(itemView.context,R.color.black_overlay))
                    binding.datMonthTxt.setTextColor(ContextCompat.getColor(itemView.context,R.color.black_overlay))
                }else{
                    binding.mailLayout.setBackgroundResource(R.drawable.time_and_date_background2)
                    binding.dayTxt.setTextColor(ContextCompat.getColor(itemView.context,R.color.white))
                    binding.datMonthTxt.setTextColor(ContextCompat.getColor(itemView.context,R.color.white))
                }

                binding.root.setOnClickListener {
                    val position = position
                    if ( position != RecyclerView.NO_POSITION){
                        lastselectedPosition = selectedPosition
                        selectedPosition = position

                        dateClickListener(date)

                        notifyItemChanged(lastselectedPosition)
                        notifyItemChanged(selectedPosition)
                    }

                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseDateViewholder {
       return CineverseDateViewholder(CineverseDateViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = timeSlots.size

    override fun onBindViewHolder(holder: CineverseDateViewholder, position: Int) {
        holder.bind(timeSlots[position])
    }
}
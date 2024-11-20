package com.example.movieapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.CineverseSeatViewholderBinding
import com.example.movieapp.domain.model.CineverseSeatModel

class CineverseSeatListAdapter(private val seatList:List<CineverseSeatModel>,
                               private val context: Context,
                               private val selectedSeat: SelectedSeat)
    :RecyclerView.Adapter<CineverseSeatListAdapter.CineverseSeatViewholder>() {

        private val selectedSeatName = ArrayList<String>()


    class CineverseSeatViewholder(val binding: CineverseSeatViewholderBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseSeatViewholder {
        return CineverseSeatViewholder(CineverseSeatViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = seatList.size

    override fun onBindViewHolder(holder: CineverseSeatViewholder, position: Int) {
        val seat = seatList[position]
        holder.binding.seatName.text = seat.name

        when ( seat.status ) {
            CineverseSeatModel.SeatStatus.AVAILABLE -> {
                holder.binding.seat.setBackgroundResource(R.drawable.available_seat)
                holder.binding.seatName.setTextColor(context.getColor(R.color.black))
            }
            CineverseSeatModel.SeatStatus.SELECTED -> {
                holder.binding.seat.setBackgroundResource(R.drawable.selected_seat)
                holder.binding.seatName.setTextColor(context.getColor(R.color.white))
            }
            CineverseSeatModel.SeatStatus.UNAVAILABLE -> {
                holder.binding.seat.setBackgroundResource(R.drawable.unavailable_seat)
                holder.binding.seatName.setTextColor(context.getColor(R.color.white))
            }
        }

        holder.binding.seat.setOnClickListener {
            when ( seat.status ) {
                CineverseSeatModel.SeatStatus.AVAILABLE ->{
                    seat.status = CineverseSeatModel.SeatStatus.SELECTED
                    selectedSeatName.add(seat.name)
                    notifyItemChanged(position)
                }
                CineverseSeatModel.SeatStatus.SELECTED ->{
                    seat.status = CineverseSeatModel.SeatStatus.AVAILABLE

                    selectedSeatName.remove(seat.name)
                    notifyItemChanged(position)
                }else -> {}

            }


            val selected = selectedSeatName.joinToString ( ",")
            selectedSeat.Retrun(selected,selectedSeatName.size)
        }
    }

    interface SelectedSeat{
        fun Retrun(selectedName: String, num: Int) {}
    }
}
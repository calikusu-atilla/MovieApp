package com.example.movieapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.CineverseSnacksViewholderBinding
import com.example.movieapp.domain.model.CineverseFoodModel

class CineverseSnacksAdapter(private val foodlist: List<CineverseFoodModel>, private val onSnackAddListener: OnSnackAddListener  ):RecyclerView.Adapter<CineverseSnacksAdapter.CineverseSnacksViewholder>() {

    interface OnSnackAddListener {
        fun onAddSnack(foodItem: CineverseFoodModel, quantity: Int)
    }

    class CineverseSnacksViewholder(val binding: CineverseSnacksViewholderBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseSnacksViewholder {
        return CineverseSnacksViewholder(CineverseSnacksViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = foodlist.size

    override fun onBindViewHolder(holder: CineverseSnacksViewholder, position: Int) {
        val foodItem = foodlist[position]
        var snacksCount = 1
        holder.binding.snacksCountTxt.text = snacksCount.toString()

        holder.binding.snacksPriceTxt.text = "$${foodItem.foodPrice}"
        holder.binding.snacksNameTxt.text = foodItem.foodName

        Glide.with(holder.itemView.context)
            .load(foodItem.foodPic)
            .apply(RequestOptions().transform(CenterCrop()))
            .into(holder.binding.snacksPic)


        holder.binding.addBtn.setOnClickListener {
            holder.binding.addBtn.visibility = View.GONE
            holder.binding.quantityLayout.visibility = View.VISIBLE

            onSnackAddListener.onAddSnack(foodItem, snacksCount)
        }

        holder.binding.increaseBtn.setOnClickListener {
            snacksCount++
            holder.binding.snacksCountTxt.text = snacksCount.toString()
        }

        holder.binding.decreaseBtn.setOnClickListener {
            if (snacksCount > 1) {
                snacksCount--
                holder.binding.snacksCountTxt.text = snacksCount.toString()
            }
        }


    }
}
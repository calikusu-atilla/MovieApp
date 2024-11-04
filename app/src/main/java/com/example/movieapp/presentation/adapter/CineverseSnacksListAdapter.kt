package com.example.movieapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.CineverseSnackslistViewholderBinding
import com.example.movieapp.domain.model.CineverseFoodModel

class CineverseSnacksListAdapter(private val SelectedSnacksList: MutableList<CineverseFoodModel>):RecyclerView.Adapter<CineverseSnacksListAdapter.CineverseSnacksListViewholder>() {

    inner class CineverseSnacksListViewholder(val binding: CineverseSnackslistViewholderBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseSnacksListViewholder {
        val binding = CineverseSnackslistViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CineverseSnacksListViewholder(binding)
    }


    override fun onBindViewHolder(holder: CineverseSnacksListViewholder, position: Int) {
        val snack = SelectedSnacksList[position]

        holder.binding.snacksNameTxt.text = snack.foodName
        holder.binding.snacksListPriceTxt.text = "$ ${snack.foodPrice}"
        holder.binding.snacksCountTxt.text = snack.quantity.toString()


        holder.binding.snacksRemoveBtn.setOnClickListener {
            SelectedSnacksList.removeAt(position)
            notifyItemRemoved(position)
        }

    }

    override fun getItemCount(): Int = SelectedSnacksList.size

    fun addSnack(snack: CineverseFoodModel, quantity: Int){
        SelectedSnacksList.add(snack)
        notifyItemInserted(SelectedSnacksList.size -1)
    }
}
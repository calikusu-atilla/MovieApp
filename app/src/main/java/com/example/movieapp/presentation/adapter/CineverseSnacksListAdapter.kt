package com.example.movieapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.CineverseSnackslistViewholderBinding
import com.example.movieapp.domain.model.CineverseFoodModel
import com.example.movieapp.presentation.viewmodel.CineverseSnackAndTicketViewModel


class CineverseSnacksListAdapter(private val selectedSnacksList: MutableList<CineverseFoodModel>) : RecyclerView.Adapter<CineverseSnacksListAdapter.CineverseSnacksListViewHolder>() {


    fun updateSnacksList(newSnacksList: List<CineverseFoodModel>) {
        selectedSnacksList.clear()
        selectedSnacksList.addAll(newSnacksList)
        notifyDataSetChanged()
    }

    inner class CineverseSnacksListViewHolder(val binding: CineverseSnackslistViewholderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseSnacksListViewHolder {
        val binding = CineverseSnackslistViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CineverseSnacksListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CineverseSnacksListViewHolder, position: Int) {

        val snack = selectedSnacksList[position]

        holder.binding.snacksNameTxt.text = snack.foodName
        holder.binding.snacksListPriceTxt.text = "$${snack.foodPrice * snack.quantity}"
        holder.binding.snacksCountTxt.text = snack.quantity.toString()

        holder.binding.snacksRemoveBtn.setOnClickListener {
            selectedSnacksList.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }


    }

    override fun getItemCount(): Int = selectedSnacksList.size

}
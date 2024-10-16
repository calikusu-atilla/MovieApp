package com.example.movieapp.presentation.adapter

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.CineverseSliderViewhoderBinding
import android.content.Context
import android.content.Intent
import com.example.movieapp.domain.model.CineverseModel
import com.example.movieapp.presentation.ui.CineverseActivity
import com.example.movieapp.presentation.ui.CineverseDetailActivity

class CineverseSliderAdapter(
    private var sliderItems: List<CineverseModel>,
    private val viewPager2: ViewPager2,
    private val activity: CineverseActivity // Activity'yi constructor'dan geçiriyoruz
) : RecyclerView.Adapter<CineverseSliderAdapter.CineverseViewHolder>() {

    private val runnable = Runnable {
        sliderItems = sliderItems
        notifyDataSetChanged()
    }

    class CineverseViewHolder(val binding: CineverseSliderViewhoderBinding): RecyclerView.ViewHolder(binding.root) {

        fun setImage(sliderItems: CineverseModel, context: Context, activity: CineverseActivity) {
            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(40))

            Glide.with(context)
                .asBitmap()
                .load(sliderItems.image)
                .apply(requestOptions)
                .into(binding.cineverseSlider)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CineverseViewHolder {
        val context = parent.context
        val binding = CineverseSliderViewhoderBinding.inflate(LayoutInflater.from(context), parent, false)
        return CineverseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CineverseViewHolder, position: Int) {
        holder.setImage(sliderItems[position], holder.itemView.context, activity)
        if (position == sliderItems.lastIndex - 1)
            viewPager2.post(runnable)

        holder.binding.cineverseSlider.setOnClickListener {
            val intent = Intent ( holder.itemView.context, CineverseDetailActivity::class.java)
            intent.putExtra("sliderobject", sliderItems[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = sliderItems.size
}

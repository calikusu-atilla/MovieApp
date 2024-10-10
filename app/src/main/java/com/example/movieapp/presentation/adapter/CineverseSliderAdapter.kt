package com.example.movieapp.presentation.adapter

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.CineverseSliderViewhoderBinding
import com.example.movieapp.domain.model.SliderModel
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.movieapp.presentation.ui.CineverseActivity
import com.google.android.material.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CineverseSliderAdapter(
    private var sliderItems: List<SliderModel>,
    private val viewPager2: ViewPager2,
    private val activity: CineverseActivity // Activity'yi constructor'dan geçiriyoruz
) : RecyclerView.Adapter<CineverseSliderAdapter.CineverseViewHolder>() {

    private val runnable = Runnable {
        sliderItems = sliderItems
        notifyDataSetChanged()
    }

    class CineverseViewHolder(val binding: CineverseSliderViewhoderBinding): RecyclerView.ViewHolder(binding.root) {

        fun setImage(sliderItems: SliderModel, context: Context, activity: CineverseActivity) {
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
    }

    override fun getItemCount(): Int = sliderItems.size
}

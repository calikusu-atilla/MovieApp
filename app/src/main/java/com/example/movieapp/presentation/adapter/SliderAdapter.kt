package com.example.movieapp.presentation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.SliderViewholderBinding
import com.example.movieapp.domain.model.SliderModel
import com.example.movieapp.presentation.ui.DetailActivity


class SliderAdapter(

    private var sliderItems: List<SliderModel>,  //Slider öğelerinin tutulduğu liste
    private val viewPager2: ViewPager2  //Slider'ın gösterileceği ViewPager bileşeni

): RecyclerView.Adapter<SliderAdapter.SliderViewholder>() {

    private val runnable = Runnable {  // ViewPager2 için otomatik geçiş işlevi
        sliderItems = sliderItems
        notifyDataSetChanged()   // notifyDataSetChanged çağrısını yaparak slider öğelerinin güncellenmesini sağlar.
    }

    class SliderViewholder(val binding: SliderViewholderBinding) : RecyclerView.ViewHolder(binding.root){ // SliderViewHolder sınıfı, RecyclerView.ViewHolder sınıfından kalıtım alır


        fun setImage(sliderItems: SliderModel, context: Context) { //Slider öğesinin görüntüsünü ayarlayan yöntem
            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(80))  // Glide için görüntü yükleme ve görüntüyü ortalayarak boyutlandırma  ve köşe yuvarlama ekleniyor seçenekleri oluşturuluyor
            Glide.with(context)   // Glide kütüphanesi kullanılarak context üzerinden görüntü yükleniyor
                .load(sliderItems.image)    // SliderModel içindeki image kullanılarak görüntü yükleniyor
                .apply(requestOptions)    // Yükleme seçenekleri uygulanıyor
                .into(binding.imageSlider)  // Yüklenen görüntü imageView bileşenine aktarılıyor

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewholder {
        val context = parent.context
        val binding = SliderViewholderBinding.inflate(LayoutInflater.from(context),parent,false)
        return SliderViewholder(binding)
    }

    override fun getItemCount(): Int = sliderItems.size

    override fun onBindViewHolder(holder: SliderViewholder, position: Int) {

        holder.setImage(sliderItems[position],holder.itemView.context)
        if (position == sliderItems.lastIndex - 1)
            viewPager2.post(runnable)


        holder.binding.nameTxt.text = sliderItems[position].name
        holder.binding.ageTxt.text = sliderItems[position].age
        holder.binding.genreTxt.text = sliderItems[position].genre
        holder.binding.yearTxt.text = sliderItems[position].year
        holder.binding.hourTxt.text = sliderItems[position].time

        holder.binding.watchBtn.setOnClickListener {

            val intent = Intent (holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("slider_object",sliderItems[position])
            holder.itemView.context.startActivity(intent)

        }

    }
}
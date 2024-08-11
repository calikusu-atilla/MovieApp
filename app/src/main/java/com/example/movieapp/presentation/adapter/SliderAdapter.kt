package com.example.movieapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R
import com.example.movieapp.domain.model.SliderModel

class SliderAdapter(

    private var sliderItems: List<SliderModel>,  //Slider öğelerinin tutulduğu liste
    private val viewPager2: ViewPager2  //Slider'ın gösterileceği ViewPager bileşeni

): RecyclerView.Adapter<SliderAdapter.SliderViewholder>() {

    private val runnable = Runnable {  // ViewPager2 için otomatik geçiş işlevi
        sliderItems = sliderItems
        notifyDataSetChanged()   // notifyDataSetChanged çağrısını yaparak slider öğelerinin güncellenmesini sağlar.
    }

    class SliderViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){ // SliderViewHolder sınıfı, RecyclerView.ViewHolder sınıfından kalıtım alır

        private var imageView : ImageView = itemView.findViewById(R.id.imageSlider)

        fun setImage(sliderItems: SliderModel, context: Context) { //Slider öğesinin görüntüsünü ayarlayan yöntem
            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(50))  // Glide için görüntü yükleme ve görüntüyü ortalayarak boyutlandırma  ve köşe yuvarlama ekleniyor seçenekleri oluşturuluyor
            Glide.with(context)   // Glide kütüphanesi kullanılarak context üzerinden görüntü yükleniyor
                .load(sliderItems.image)    // SliderModel içindeki image kullanılarak görüntü yükleniyor
                .apply(requestOptions)    // Yükleme seçenekleri uygulanıyor
                .into(imageView)  // Yüklenen görüntü imageView bileşenine aktarılıyor


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewholder {
        val context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_viewholder,parent,false)
        return SliderViewholder(view)
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    override fun onBindViewHolder(holder: SliderViewholder, position: Int) {

        holder.setImage(sliderItems[position],holder.itemView.context)
        if (position == sliderItems.lastIndex - 1)
            viewPager2.post(runnable)


    }
}
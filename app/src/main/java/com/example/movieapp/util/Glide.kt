package com.example.movieapp.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R

// ImageView'e bir URL'den resim indirme işlemi yapacak uzantı fonksiyonu tanımlıyoruz.
// Bu fonksiyon, CircularProgressDrawable kullanarak yükleme sırasında gösterim yapar.
fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable) {


    val requestOptions = RequestOptions() // ayarlamaları yapıyoruz
        .transform(CenterCrop(), RoundedCorners(30)) // Resmi kırp ve köşeleri yuvarla
        .error(R.mipmap.ic_launcher_round) // Resim yüklenemezse gösterilecek simge
        .placeholder(progressDrawable) // Yükleme sırasında gösterilecek dönen simge


    Glide.with(context)  // Glide ile resmi yüklüyoruz.
        .setDefaultRequestOptions(requestOptions) // Önceden belirlenmiş seçenekleri uygula
        .load(url)  // Resmi URL'den yükle
        .into(this) // Bu ImageView'e yüklenen resmi göster

}

// CircularProgressDrawable oluşturup ayarlayan fonksiyon.
fun placeholderProgressBar(context: Context): CircularProgressDrawable {

    // CircularProgressDrawable oluşturuluyor ve özellikleri belirleniyor.
    return CircularProgressDrawable(context).apply {

        strokeWidth = 8f // Çizginin kalınlığı 8 dp olarak ayarlanıyor
        centerRadius = 40f // Çemberin yarıçapı 40 dp olarak ayarlanıyor
        start()  // Dönen simgeyi başlat
    }
}

// ImageView'e resmi yüklemek için downloadFromUrl fonksiyonunu çağırıyor.
fun dowloadImage(view: ImageView, string: String?) {

    // downloadFromUrl fonksiyonunu çağırıyoruz ve yükleme simgesi olarak CircularProgressDrawable kullanıyoruz.
    view.downloadFromUrl(url = null, placeholderProgressBar(view.context))
}
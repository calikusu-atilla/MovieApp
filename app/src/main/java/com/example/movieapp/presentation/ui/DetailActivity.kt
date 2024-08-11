package com.example.movieapp.presentation.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.ActivityDetailBinding
import com.example.movieapp.domain.model.TopMoviesModel
import com.example.movieapp.presentation.adapter.CategoryEachFilmAdapter
import eightbitlab.com.blurview.RenderScriptBlur

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var topMovie : TopMoviesModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setVeriable()
    }

    private fun setVeriable() {
        topMovie = intent.getParcelableExtra("object")!!

        val requestOptions = RequestOptions().transform(CenterCrop(), GranularRoundedCorners(0f,0f,50f,50f))
        Glide.with(this)
            .load(topMovie.poster)
            .apply(requestOptions)
            .into(binding.filmPic)



        binding.titleTxt.text = topMovie.title
        binding.imdbTxt.text = "IMDB " + topMovie.Imdb.toString()
        binding.movieTimesTxt.text = topMovie.year.toString() + " - " + topMovie.time.toString()
        binding.movieSummery.text = topMovie.description

        binding.watchTrailerBtn.setOnClickListener{

            val id = topMovie.trailer.replace("https://www.youtube.com/watch?v=", "")
            val appIntent = Intent (Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id))
            val webIntent = Intent (Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + id))

            try {
                startActivity(appIntent)
            }catch (ex: ActivityNotFoundException) {
                startActivity(webIntent)
            }
        }

        binding.backBtn.setOnClickListener { finish() }

        val radius = 10f

        val decorView: View = window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val  windowsBackground = decorView.background

        binding.blurView.setupWith(rootView,RenderScriptBlur(this))
            .setFrameClearDrawable(windowsBackground)
            .setBlurRadius(radius)

        binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND)
        binding.blurView.clipToOutline = true

        if (topMovie.genre != null){
            binding.genreView.adapter = CategoryEachFilmAdapter(topMovie.genre)
            binding.genreView.layoutManager = LinearLayoutManager(this@DetailActivity,LinearLayoutManager.HORIZONTAL,false)

        }




    }
}
package com.example.movieapp.presentation.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.ActivityDetailBinding
import com.example.movieapp.domain.model.CastModel
import com.example.movieapp.domain.model.SliderModel
import com.example.movieapp.domain.model.TopMoviesModel
import com.example.movieapp.domain.model.UpcomingMovieDetailModel
import com.example.movieapp.presentation.adapter.CategoryEachFilmAdapter
import com.example.movieapp.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur

@AndroidEntryPoint
class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var topMovie : TopMoviesModel
    private lateinit var cast : CastModel
    private lateinit var slider : SliderModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        topMovieId()
    }

    private fun topMovieId() {
        val movieId = intent.getIntExtra("movieId", -1)
        Log.d("DetailActivity", "Movie ID: $movieId")
        if (movieId != -1) {
            val topMovieFromIntent = intent.getParcelableExtra<TopMoviesModel>("object")
            if (topMovieFromIntent != null) {
                topMovie = topMovieFromIntent // topMovie'yu burada ayarlıyoruz
                Log.d("DetailActivity", "TopMovie initialized: $topMovie")
                setVeriable() // setVeriable burada çağrılmalı
                showTopMovieDetail(topMovie)
            } else {
                getupcomingMovieDetails(movieId)
            }
        } else {
            Log.e("DetailActivity", "Geçersiz movieId")
        }
    }

    /*
    private fun topMovieId() {
        val movieId = intent.getIntExtra("movieId", -1)
        if (movieId != -1) {
            getupcomingMovieDetails(movieId)
        }
    } */

    private fun getupcomingMovieDetails(movieId: Int) {

        // ViewModel'den film detaylarını gözlemle
        viewModel.movieDetails.observe(this, Observer<UpcomingMovieDetailModel?> { movieDetails ->
            Log.d("DetailActivity", "Upcoming movies details: $movieDetails")
            movieDetails?.let { showUpcomingMovieDetail(it) }
        })
        viewModel.getMovieDetails(movieId)

    }


    private fun showUpcomingMovieDetail(details: UpcomingMovieDetailModel){
        val requestOptions = RequestOptions().transform(CenterCrop(), GranularRoundedCorners(0f, 0f,50f,50f))
        Glide.with(this)
            .load(details.poster)
            .apply(requestOptions)
            .into(binding.filmPic)

        binding.titleTxt.text = details.title
        binding.imdbTxt.text = "IMDB " + details.imdb.toString()
        binding.movieTimesTxt.text = details.year.toString() + " - " + details.time.toString()
        binding.movieSummery.text = details.description

        /*
        if (details.genre.isNotEmpty()) {

            binding.genreView.adapter = CategoryEachFilmAdapter(details.genre)
            binding.genreView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false )
        } */

    }

    private fun showTopMovieDetail(topMovie: TopMoviesModel) {
        val requestOptions = RequestOptions().transform(CenterCrop(), GranularRoundedCorners(0f, 0f, 50f, 50f))
        Glide.with(this)
            .load(topMovie.poster)
            .apply(requestOptions)
            .into(binding.filmPic)

        binding.titleTxt.text = topMovie.title
        binding.imdbTxt.text = "IMDB " + topMovie.Imdb.toString()
        binding.movieTimesTxt.text = topMovie.year.toString() + " - " + topMovie.time.toString()
        binding.movieSummery.text = topMovie.description

    }

    private fun setVeriable() {
        Log.d("DetailActivity", "Setting variables")
        /*
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
        */

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

        /*if (topMovie.casts.isNotEmpty()) {

            binding.castView.layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }*/



    }
}
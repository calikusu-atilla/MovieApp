package com.example.movieapp.presentation.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.ActivityCineverseDetailBinding
import com.example.movieapp.domain.model.CineverseModel
import com.example.movieapp.presentation.adapter.CineverseCastListAdapter
import com.example.movieapp.presentation.adapter.CineverseRecommendedAdapter
import com.example.movieapp.presentation.adapter.CineverseReviewsListAdapter

class CineverseDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityCineverseDetailBinding
    private lateinit var movieDetail: CineverseModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCineverseDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        setVeriable()

    }

    private fun setVeriable() {

        movieDetail = intent.getParcelableExtra<CineverseModel>("sliderobject")!!

        val requestOptions = RequestOptions().transform(CenterCrop(), GranularRoundedCorners(0f,0f,100f,100f))
        Glide.with(this)
            .load(movieDetail.image)
            .apply(requestOptions)
            .into(binding.filmPic)

        binding.nameTxt.text = movieDetail.name
        binding.genreTxt.text = movieDetail.genre
        binding.imdbTxt.text = movieDetail.Imdb
        binding.yearTxt.text = movieDetail.year
        binding.hourTxt.text = movieDetail.time
        binding.ageTxt.text = movieDetail.age
        binding.movieDescriptionTxt.text = movieDetail.description

        movieDetail.casts?.let {
            binding.castView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
            binding.castView.adapter = CineverseCastListAdapter(it)
        }

        if (movieDetail.recommended.isNullOrEmpty()) {
            binding.recommendedProgressBar.visibility = View.VISIBLE
            binding.recommendedView.visibility = View.GONE
        } else {
            binding.recommendedProgressBar.visibility = View.GONE
            binding.recommendedView.visibility = View.VISIBLE
            binding.recommendedView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
            binding.recommendedView.adapter = CineverseRecommendedAdapter(movieDetail.recommended)
        }


        if (movieDetail.reviews.isNullOrEmpty()) {
            binding.reviewsProgressBar.visibility = View.VISIBLE
            binding.reviewsView.visibility = View.GONE
        } else {
            binding.reviewsProgressBar.visibility = View.GONE
            binding.reviewsView.visibility = View.VISIBLE
            binding.reviewsView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.reviewsView.adapter = CineverseReviewsListAdapter(movieDetail.reviews)
            binding.reviewsSizeTxt.text = "${movieDetail.reviews.size}"
        }


        binding.backBtn.setOnClickListener { finish() }
        binding.watchTrailerBtn.setOnClickListener {

            val id = movieDetail.trailer.replace("https://www.youtube.com/watch?v=", "")
            val appIntent = Intent (Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id))
            val webIntent = Intent (Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + id))

            try {
                startActivity(appIntent)
            }catch (ex: ActivityNotFoundException) {
                startActivity(webIntent)
            }

        }


    }
}
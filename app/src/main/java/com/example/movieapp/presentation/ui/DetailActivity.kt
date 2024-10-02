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
import com.example.movieapp.domain.model.MovieTmdbApıVideosModel
import com.example.movieapp.domain.model.SliderModel
import com.example.movieapp.domain.model.TmdbApiCastModel
import com.example.movieapp.domain.model.TopMoviesModel
import com.example.movieapp.domain.model.UpcomingMovieDetailModel
import com.example.movieapp.presentation.adapter.CastListAdapter
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

        if (intent.hasExtra("object")) { // Eğer "object" varsa, setVeriable() çağrılır, yoksa upcoming movie detayını al
            setVeriable()
        } else {
            topMovieId()
        }

    }


    private fun topMovieId() {
        val movieId = intent.getIntExtra("movieId", -1)
        Log.d("DetailActivity", "API Movie ID: $movieId")

        if (movieId != -1) {
            getupcomingMovieDetails(movieId)
        } else {
            Log.e("DetailActivity", "Geçersiz movieId")
        }
    }


    private fun getupcomingMovieDetails(movieId: Int) {

        // ViewModel'den film detaylarını gözlemle
        viewModel.movieDetails.observe(this, Observer<UpcomingMovieDetailModel?> { movieDetails ->
            Log.d("DetailActivity", "Upcoming movies details: $movieDetails")
            movieDetails?.let { showUpcomingMovieDetail(it) }
        })
        viewModel.getMovieDetails(movieId)

        viewModel.movieVideo.observe(this, Observer<MovieTmdbApıVideosModel?> { videos ->
            videos?.let{
                // Kullanıcı arayüzünü video verileriyle güncelle
                Log.d("DetailActivity", "Fetched videos: $videos")
                // Burada kullanıcı arayüzünü güncelleyebilirsiniz
               // updateVideoUI(it.videos) // it.videos'ın VideoModel listesi olduğunu varsayıyoruz
            }
        })
        viewModel.getMovieDetails(movieId)


    }

    private fun castMovıeId(movieId: Int) {

        viewModel.castMovies.observe(this, Observer { cast ->
            Log.d("DetailActivity", "Cast : $cast")
            cast?.let { showCastList(it) }
        })
        viewModel.getCastMovies(movieId)


    }

    private fun showCastList(castList: List<TmdbApiCastModel?>) {
        if (castList.isNotEmpty()) {
            val adapter = CastListAdapter(castList)
            binding.castView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
            binding.castView.adapter = adapter
        }

    }


    private fun showUpcomingMovieDetail(details: UpcomingMovieDetailModel){

        val requestOptions = RequestOptions().transform(CenterCrop(), GranularRoundedCorners(0f, 0f,50f,50f))
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${details.poster}")
            .apply(requestOptions)
            .into(binding.filmPic)


        binding.titleTxt.text = details.title
        binding.imdbTxt.text = "IMDB " + details.imdb
        binding.movieTimesTxt.text = details.year.toString() + " - " + details.time.toString()
        binding.movieSummery.text = details.description

        if (details.genre != null){
            binding.genreView.adapter = CategoryEachFilmAdapter(details.genre)
            binding.genreView.layoutManager = LinearLayoutManager(this@DetailActivity,LinearLayoutManager.HORIZONTAL,false)

        }
        castMovıeId(movieId = details.uuid)


        setupBlurView()

        binding.watchTrailerBtn.setOnClickListener{

            val id = details.trailer.replace("https://www.youtube.com/watch?v=", "")
            val appIntent = Intent (Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id))
            val webIntent = Intent (Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + id))

            try {
                startActivity(appIntent)
            }catch (ex: ActivityNotFoundException) {
                startActivity(webIntent)
            }
        }

        binding.backBtn.setOnClickListener { finish() }

        setupBlurView()

    }

    private fun setVeriable() {

        topMovie = intent.getParcelableExtra("object")!!

        Log.d("DetailActivity", "Firebase Movie ID: $topMovie")


        val requestOptions = RequestOptions().transform(CenterCrop(), GranularRoundedCorners(0f,0f,50f,50f))
        Glide.with(this)
            .load(topMovie.poster)
            .apply(requestOptions)
            .into(binding.filmPic)


        binding.titleTxt.text = topMovie.title
        binding.imdbTxt.text = "IMDB " + topMovie.Imdb.toString()
        binding.movieTimesTxt.text = topMovie.year.toString() + " - " + topMovie.time.toString()
        binding.movieSummery.text = topMovie.description

        if (topMovie.genre != null){
            binding.genreView.adapter = CategoryEachFilmAdapter(topMovie.genre)
            binding.genreView.layoutManager = LinearLayoutManager(this@DetailActivity,LinearLayoutManager.HORIZONTAL,false)

        }


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

        setupBlurView()

    }


    private fun setupBlurView() {
        val radius = 10f
        val decorView: View = window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowsBackground = decorView.background

        binding.blurView.setupWith(rootView,RenderScriptBlur(this))
            .setFrameClearDrawable(windowsBackground)
            .setBlurRadius(radius)

        binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND)
        binding.blurView.clipToOutline = true

    }

}
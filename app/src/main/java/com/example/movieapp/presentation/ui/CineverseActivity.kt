package com.example.movieapp.presentation.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityCineverseBinding
import com.example.movieapp.domain.model.CineverseModel
import com.example.movieapp.presentation.adapter.CineverseSliderAdapter
import com.example.movieapp.presentation.viewmodel.CineverseViewModel
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur

@AndroidEntryPoint
class CineverseActivity : BaseActivity() {
    private lateinit var binding: ActivityCineverseBinding
    private var sliderItems: List<CineverseModel> = emptyList()
    private val viewModel: CineverseViewModel by viewModels()
    private val sliderHandler = android.os.Handler() // Slider için Handler
    private val sliderRunnable = Runnable { // Slider için Runnable
        binding.viewPagerCineverseSlider.setCurrentItem(binding.viewPagerCineverseSlider.currentItem + 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCineverseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initCineverseSlider()
        bottomNavigation()

    }

    private fun initCineverse(items: CineverseModel){

        binding.nameTxt.text = items.name
        binding.ageTxt.text = items.age
        binding.genreTxt.text = items.genre
        binding.hourTxt.text = items.time
        binding.yearTxt.text = items.year
        binding.imdbTxt.text = items.Imdb

        binding.watchTrailerBtn.setOnClickListener{

            val trailerId = items.trailer.replace("https://www.youtube.com/watch?v=", "")
            val appIntent = Intent( Intent.ACTION_VIEW,Uri.parse("vnd.youtube:" + trailerId))
            val webIntent = Intent( Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v=" + trailerId))

                    try {
                        startActivity(appIntent)
                    }catch (ex: ActivityNotFoundException) {
                        startActivity(webIntent)
                    }
                }

    }

    private fun initCineverseSlider() {
        binding.progressBarCineverse.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer { banners ->
            if (banners.isNotEmpty()) {
                sliderItems = banners
                initCineverse(banners[4])
                setupSlider(banners)
            }
            binding.progressBarCineverse.visibility = View.GONE
            binding.viewPagerCineverseSlider.visibility = View.VISIBLE
        })
        binding.viewPagerCineverseSlider.visibility = View.GONE
        viewModel.loadCineverseBanner()
    }

    private fun setupSlider(images: List<CineverseModel>){

        //setupBlurView()

        binding.viewPagerCineverseSlider.adapter = CineverseSliderAdapter(images,binding.viewPagerCineverseSlider)
        binding.viewPagerCineverseSlider.clipChildren = false
        binding.viewPagerCineverseSlider.clipToPadding = false
        binding.viewPagerCineverseSlider.offscreenPageLimit = 1
        binding.viewPagerCineverseSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(30))
            addTransformer(ViewPager2.PageTransformer { page, position ->
                val  r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            })
        }
        binding.viewPagerCineverseSlider.setPageTransformer(compositePageTransformer)
        binding.viewPagerCineverseSlider.currentItem = 1
        binding.viewPagerCineverseSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)

                /*

                     val imageUrl = images[position].image // Slider'daki resim URL'si
                        Glide.with(this@CineverseActivity)
                           .load(imageUrl)
                           .transform(jp.wasabeef.glide.transformations.BlurTransformation(blurLevel, 5), FitCenter())
                           .into(binding.backgroundImageView)

               */

            }
        })

    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        sliderHandler.removeCallbacksAndMessages(null)
    }

    private fun bottomNavigation() {

        binding.chipNavigationBar.setOnItemSelectedListener { id ->
            when(id) {
                R.id.ticket -> {
                    val intent = Intent (this, CineverseTicketListActivity::class.java)
                    startActivity(intent) }
                R.id.home -> {
                    val intent = Intent (this, CineverseActivity::class.java)
                    startActivity(intent) }
                R.id.movie -> {
                    val intent = Intent ( this, MainActivity::class.java)
                    startActivity(intent) }
                R.id.search -> {

                }
                else  -> {

                }
            }
        }
    }

    private fun setupBlurView() {
        val radius = 10f
        val decorView: View = window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowsBackground = decorView.background

        binding.blurView.setupWith(rootView, RenderScriptBlur(this))
            .setFrameClearDrawable(windowsBackground)
            .setBlurRadius(radius)

        binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND)
        binding.blurView.clipToOutline = true


    }

}
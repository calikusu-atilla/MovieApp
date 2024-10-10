package com.example.movieapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.databinding.ActivityCineverseBinding
import com.example.movieapp.domain.model.SliderModel
import com.example.movieapp.presentation.adapter.CineverseSliderAdapter
import com.example.movieapp.presentation.viewmodel.CineverseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CineverseActivity : BaseActivity() {
    private lateinit var binding: ActivityCineverseBinding
    private var sliderItems: List<SliderModel> = emptyList()
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

    }

    private fun initCineverse(items: SliderModel){

        binding.nameTxt.text = items.name
        binding.ageTxt.text = items.age
        binding.genreTxt.text = items.genre
        binding.hourTxt.text = items.time
        binding.yearTxt.text = items.year

    }

    private fun initCineverseSlider() {
        binding.progressBarCineverse.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer { banners ->
            if (banners.isNotEmpty()) {
                sliderItems = banners
                initCineverse(banners[0])
                setupSlider(banners)
            }
            binding.progressBarCineverse.visibility = View.GONE
            binding.viewPagerCineverseSlider.visibility = View.VISIBLE
        })
        binding.viewPagerCineverseSlider.visibility = View.GONE
        viewModel.loadCineverseBanner()
    }

    private fun setupSlider(images: List<SliderModel>){

        binding.viewPagerCineverseSlider.adapter = CineverseSliderAdapter(images,binding.viewPagerCineverseSlider, this)
        binding.viewPagerCineverseSlider.clipChildren = false
        binding.viewPagerCineverseSlider.clipToPadding = false
        binding.viewPagerCineverseSlider.offscreenPageLimit = 1
        binding.viewPagerCineverseSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(60))
            addTransformer(ViewPager2.PageTransformer { page, position ->
                val  r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            })
        }
        binding.viewPagerCineverseSlider.setPageTransformer(compositePageTransformer)
        binding.viewPagerCineverseSlider.setCurrentItem(binding.viewPagerCineverseSlider.currentItem + 1, true)
        binding.viewPagerCineverseSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                initCineverse(images[position])

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

}
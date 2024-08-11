package com.example.movieapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.domain.model.SliderModel
import com.example.movieapp.presentation.adapter.SliderAdapter
import com.example.movieapp.presentation.adapter.TopMoviesAdapter
import com.example.movieapp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val sliderHandler = android.os.Handler() // Slider için Handler
    private val sliderRunnable = Runnable { // Slider için Runnable
        binding.viewPagerSlider.setCurrentItem(binding.viewPagerSlider.currentItem + 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        initBanners()
        initTopMovies()

    }

    private fun initTopMovies() {
        binding.progressBarTop.visibility = View.VISIBLE

        viewModel.topMovies.observe(this, Observer {
            binding.recyclerViewTop.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
            binding.recyclerViewTop.adapter = TopMoviesAdapter(it)
            binding.progressBarTop.visibility = View.GONE
        })
        viewModel.topMovies()
    }

    private fun initBanners() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer {
            banners(it)
            binding.progressBarBanner.visibility = View.GONE
        })
        viewModel.loadBanner()
    }

    private fun banners (images: List<SliderModel>){
        binding.viewPagerSlider.adapter = SliderAdapter(images,binding.viewPagerSlider)
        binding.viewPagerSlider.clipChildren = false
        binding.viewPagerSlider.clipToPadding = false
        binding.viewPagerSlider.offscreenPageLimit = 1
        binding.viewPagerSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(60))
            addTransformer(ViewPager2.PageTransformer { page, position ->
                val  r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            })
        }
        binding.viewPagerSlider.setPageTransformer(compositePageTransformer)
        binding.viewPagerSlider.currentItem = 1
        binding.viewPagerSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)

            }
        })

    }

}
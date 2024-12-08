package com.example.movieapp.presentation.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.databinding.FragmentCineverseHomeBinding
import com.example.movieapp.domain.model.CineverseModel
import com.example.movieapp.presentation.adapter.CineverseSliderAdapter
import com.example.movieapp.presentation.viewmodel.CineverseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CineverseHomeFragment : Fragment() {

    private lateinit var binding: FragmentCineverseHomeBinding
    private var sliderItems: List<CineverseModel> = emptyList()
    private val viewModel: CineverseViewModel by viewModels()
    private val sliderHandler = android.os.Handler() // Slider için Handler
    private val sliderRunnable = Runnable { // Slider için Runnable
        binding.viewPagerCineverseSlider.setCurrentItem(binding.viewPagerCineverseSlider.currentItem + 1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCineverseHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCineverseSlider()
    }
    private fun initCineverse(items: CineverseModel) {
        binding.nameTxt.text = items.name
        binding.ageTxt.text = items.age
        binding.genreTxt.text = items.genre
        binding.hourTxt.text = items.time
        binding.yearTxt.text = items.year
        binding.imdbTxt.text = items.Imdb

        binding.watchTrailerBtn.setOnClickListener {
            val trailerId = items.trailer.replace("https://www.youtube.com/watch?v=", "")
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$trailerId"))
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$trailerId"))

            try {
                startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                startActivity(webIntent)
            }
        }
    }

    private fun initCineverseSlider() {
        binding.progressBarCineverse.visibility = View.VISIBLE
        viewModel.banners.observe(viewLifecycleOwner, Observer { banners ->
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

    private fun setupSlider(images: List<CineverseModel>) {
        binding.viewPagerCineverseSlider.adapter =
            CineverseSliderAdapter(images, binding.viewPagerCineverseSlider)
        binding.viewPagerCineverseSlider.clipChildren = false
        binding.viewPagerCineverseSlider.clipToPadding = false
        binding.viewPagerCineverseSlider.offscreenPageLimit = 1
        binding.viewPagerCineverseSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(60))
            addTransformer(ViewPager2.PageTransformer { page, position ->
                val r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            })
        }
        binding.viewPagerCineverseSlider.setPageTransformer(compositePageTransformer)
        binding.viewPagerCineverseSlider.currentItem = 1
        binding.viewPagerCineverseSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)

                // Blur arka plan işlemi yapılabilir
                // Glide kullanarak background güncellemesi buraya eklenebilir.
            }
        })
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sliderHandler.removeCallbacksAndMessages(null)
    }
}
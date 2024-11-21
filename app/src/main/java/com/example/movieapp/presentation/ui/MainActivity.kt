package com.example.movieapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.data.repository.AuthRepository
import com.example.movieapp.data.source.FirebaseAuthManager
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.domain.model.SliderModel
import com.example.movieapp.presentation.adapter.SliderAdapter
import com.example.movieapp.presentation.adapter.TopMoviesAdapter
import com.example.movieapp.presentation.adapter.TredingMoviesAdapter
import com.example.movieapp.presentation.adapter.UpcomingMoviesAdapter
import com.example.movieapp.presentation.viewmodel.AuthViewModel
import com.example.movieapp.presentation.viewmodel.AuthViewModelFactory
import com.example.movieapp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val authViewModel: AuthViewModel by viewModels { AuthViewModelFactory(AuthRepository(FirebaseAuthManager(this))) }
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

        checkCunrentUser()
        observeAuthState()
        bottomNavigation()
        initBanners()
        initTopMovies()
        initUpcomingMovies()
        initTredingMovies()

    }

    private fun initTredingMovies() {
        binding.recyclerViewTreding.visibility = View.VISIBLE

        viewModel.tredingMovies.observe(this, Observer { tredingMovies ->
            Log.d("MainActivity", "Treding movies count: ${tredingMovies.size}")
            binding.recyclerViewTreding.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
            binding.recyclerViewTreding.adapter = TredingMoviesAdapter(tredingMovies)
            binding.progressBarTreding.visibility = View.GONE

        })
        viewModel.tredingMovies("day")
    }

    private fun initUpcomingMovies() {

        binding.recyclerViewUpcoming.visibility = View.VISIBLE

        viewModel.upcomingMovies.observe(this, Observer { movies ->
            Log.d("MainActivity", "Upcoming movies count: ${movies.size}")
            binding.recyclerViewUpcoming.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
            //binding.recyclerViewUpcoming.layoutManager = GridLayoutManager(this@MainActivity,2)
            binding.recyclerViewUpcoming.adapter = UpcomingMoviesAdapter(movies)
            binding.progressBarUpcoming.visibility = View.GONE
            Log.d("MainActivity", "1")
        })
        Log.d("MainActivity", "2")
        viewModel.loadUpcomingMovies(2)

    }

    private fun checkCunrentUser() {
        val currentUser = authViewModel.getCurrentUser()
        if (currentUser == null) {
            Log.d("ProfilActivity", "Kullanıcı oturum açmamış.")
            Toast.makeText(this, "Kullanıcı oturum açmamış. Lütfen giriş yapın.", Toast.LENGTH_LONG).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }else {
            Log.d("MainActivity", "Kullanıcı oturum açmış durumda: ${currentUser.email}")
        }
    }

    private fun observeAuthState() {
        authViewModel.authState.observe(this, Observer { isAuthenticated ->
            if (!isAuthenticated) {
                // Oturum kapatıldı, LoginActivity'ye yönlendir
                Log.d("ProfilActivity", "Kullanıcı oturumu kapatıldı.")
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun logOutUser(){
        Log.d("MainActivity", "Oturum kapatılıyor...")
        authViewModel.logout()
        checkCunrentUser()

    }

    private fun bottomNavigation() {
        binding.profilBtn.setOnClickListener { startActivity(Intent(this@MainActivity,ProfilActivity::class.java))}
        binding.mainBtn.setOnClickListener{ startActivity(Intent(this@MainActivity,MainActivity::class.java))}
        binding.cineverseBtn.setOnClickListener { startActivity(Intent(this@MainActivity,CineverseActivity::class.java)) }
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
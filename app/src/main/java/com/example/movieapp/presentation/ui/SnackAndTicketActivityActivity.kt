package com.example.movieapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.databinding.ActivitySnackAndTicketActivityBinding
import com.example.movieapp.domain.model.CineverseFoodModel
import com.example.movieapp.domain.model.CineverseModel
import com.example.movieapp.presentation.adapter.CineverseSnacksAdapter
import com.example.movieapp.presentation.adapter.CineverseSnacksListAdapter
import com.example.movieapp.presentation.viewmodel.CineverseSnackAndTicketViewModel
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur

@AndroidEntryPoint
class SnackAndTicketActivityActivity : BaseActivity() {

    private lateinit var binding: ActivitySnackAndTicketActivityBinding
    private lateinit var movie: CineverseModel
    private val viewModel: CineverseSnackAndTicketViewModel by viewModels()
    private var totalPrice: Double = 0.0
    private var seatCount: Int = 0
    private var selectedSeats: String ?= null
    private var selectedTime: String ?= null
    private var selectedDate: String ?= null
    private var convenienceFees: Double = 30.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySnackAndTicketActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        setVeriable()
        initSnacks()
        selectedSnacksList()



    }

    private fun initSnacks() {
        binding.progressBarSnacks.visibility = View.VISIBLE
        viewModel.foodlist.observe(this) { foodlist ->
            Log.d("SnackAndTicketActivity", "Food list details: $foodlist")

            if (foodlist != null) {
                binding.recyclerViewSnacks.visibility = View.VISIBLE
                binding.recyclerViewSnacks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                binding.recyclerViewSnacks.adapter = CineverseSnacksAdapter(foodlist,object : CineverseSnacksAdapter.OnSnackAddListener{
                    override fun onAddSnack(foodItem: CineverseFoodModel, quantity: Int) {
                        addSnackToSelectedList(foodItem, 1)
                    }

                })
            } else {
                binding.recyclerViewSnacks.visibility = View.GONE
            }

            binding.progressBarSnacks.visibility = View.GONE
        }

        viewModel.loadFoodList()
    }

    private fun setVeriable() {

        getIntentExtra()

        val requestOptions = RequestOptions().transform(CenterCrop(),GranularRoundedCorners(0f,0f,100f,100f))
        Glide.with(this)
            .load(movie.image)
            .apply(requestOptions)
            .into(binding.filmPic)

        binding.nameTxt.text = movie.name
        binding.seatsTxt.text = selectedSeats ?: "Seçilen Koltuk Yok"
        binding.dateTxt.text = selectedDate
        binding.timeTxt.text = selectedTime
        binding.seatCount.text = seatCount.toString()
        binding.totalPriceTxt.text = totalPrice.toString()
        binding.convenienceFeesPriceTxt.text = convenienceFees.toString()

        setupBlurView()

    }

    private fun selectedSnacksList(){
        val adapter = CineverseSnacksListAdapter(mutableListOf())
        binding.recyclerViewSnacksList.adapter = adapter
        binding.recyclerViewSnacksList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)


        viewModel.selectedSnacks.observe(this){ selectedSnacks ->
            selectedSnacks.lastOrNull()?.let { snackItem ->
                adapter.addSnack(snackItem, 1)
            }
        }
    }

    private fun addSnackToSelectedList(foodItem: CineverseFoodModel, quantity: Int){
        viewModel.addSelectedSnack(foodItem)
    }


    private fun getIntentExtra () {

        movie = intent.getParcelableExtra("seatListMovie")!!
        totalPrice = intent.getDoubleExtra("totalPrice",0.0)
        seatCount = intent.getIntExtra("seatCount", 0)
        selectedSeats = intent.getStringArrayListExtra("selectedSeats").toString()
        selectedDate = intent.getStringExtra("selectedDate")
        selectedTime = intent.getStringExtra("selectedTime")



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
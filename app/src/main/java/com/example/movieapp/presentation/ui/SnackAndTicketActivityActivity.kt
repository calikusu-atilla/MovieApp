package com.example.movieapp.presentation.ui

import CineverseSnacksAdapter
import android.content.Intent
import android.os.Bundle
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
import com.example.movieapp.domain.model.CineverseModel
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
    private var totalAmount: Double = 0.0
    private var seatCount: Int = 0
    private var selectedSeats: String? = null
    private var selectedTime: String? = null
    private var selectedDate: String? = null
    private var convenienceFees: Double = 30.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySnackAndTicketActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setVeriable()
        initSnacks()
        selectedSnacksList()
    }

    private fun setVeriable() {

        setupBlurView()
        getIntentExtra()
        bottomNavigation()

        val requestOptions = RequestOptions().transform(GranularRoundedCorners(0f, 0f, 100f, 100f))
        Glide.with(this)
            .load(movie.image)
            .centerInside()
            .apply(requestOptions)
            .into(binding.filmPic)

        binding.nameTxt.text = movie.name
        binding.chapterTxt.text = movie.chapter
        binding.seatsTxt.text = selectedSeats ?: "Seçilen Koltuk Yok"
        binding.dateTxt.text = selectedDate
        binding.timeTxt.text = selectedTime
        binding.seatCount.text = seatCount.toString()
        binding.totalPriceTxt.text = totalPrice.toString()
        binding.convenienceFeesPriceTxt.text = convenienceFees.toString()

        viewModel.snacksListTotalPrice.observe(this) { snacksTotalPrice ->
            binding.snacksTotalPrice.text = "$$snacksTotalPrice"

        }

        viewModel.snacksListTotalPrice.observe(this) {snacksTotalPrice ->
            totalAmount = (snacksTotalPrice + convenienceFees + totalPrice)
            binding.totalAmount.text = totalAmount.toString()
        }

        binding.bookTicketsBtn.setOnClickListener {

            viewModel.ticketSave(
                movie = movie.name, movieScreen = movie.chapter,
                date = selectedDate ?: "", time = selectedTime ?: "",
                seats = selectedSeats ?: "", totalPrice = totalAmount,
                picUrl = movie.image
            )

            viewModel.ticketId.observe(this) { ticketId ->
                if (ticketId !=null) {
                    val intent = Intent(this@SnackAndTicketActivityActivity, CineversePaymentOkActivity::class.java)
                    intent.putExtra("seatMovie", movie)
                    intent.putExtra("selectedDate", selectedDate)
                    intent.putExtra("selectedTime", selectedTime)
                    intent.putExtra("seats", selectedSeats)
                    intent.putExtra("ticketId", ticketId)
                    startActivity(intent)
                }
            }
        }
    }



    private fun initSnacks() {
        binding.progressBarSnacks.visibility = View.VISIBLE
        viewModel.foodlist.observe(this) { foodlist ->
            binding.progressBarSnacks.visibility = View.GONE
            foodlist?.let {
                binding.recyclerViewSnacks.apply {
                    visibility = View.VISIBLE
                    layoutManager = LinearLayoutManager(this@SnackAndTicketActivityActivity, LinearLayoutManager.HORIZONTAL, false)
                    adapter = CineverseSnacksAdapter(it,viewModel)
                }
            } ?: run {
                binding.recyclerViewSnacks.visibility = View.GONE
            }
        }
        viewModel.loadFoodList()
    }

    private fun selectedSnacksList() {
        val adapter = CineverseSnacksListAdapter(mutableListOf())
        binding.recyclerViewSnacksList.adapter = adapter
        binding.recyclerViewSnacksList.layoutManager = LinearLayoutManager(this@SnackAndTicketActivityActivity, LinearLayoutManager.VERTICAL, false)

        viewModel.selectedSnacks.observe(this) {selectedSnacksList ->
            adapter.updateSnacksList(selectedSnacksList)

            if (selectedSnacksList.isNullOrEmpty()) {
                totalAmount = 0.0
            }else {
                val snacksTotalPrice = selectedSnacksList.sumOf { it.foodPrice * it.quantity }
                totalAmount = (snacksTotalPrice + convenienceFees + totalPrice)
            }
            binding.totalAmount.text = totalAmount.toString()
        }
    }

    private fun getIntentExtra() {
        movie = intent.getParcelableExtra("seatListMovie")!!
        totalPrice = intent.getDoubleExtra("totalPrice", 0.0)
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

    private fun bottomNavigation() {
        //startActivity(Intent(this@SnackAndTicketActivityActivity, CinemaTicketDetailActivity::class.java))
    }
}
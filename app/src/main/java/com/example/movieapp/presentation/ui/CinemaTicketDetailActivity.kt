package com.example.movieapp.presentation.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityCinemaTicketDetailBinding
import com.example.movieapp.domain.model.CineverseModel
import com.example.movieapp.presentation.fragment.CineverseHomeFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CinemaTicketDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityCinemaTicketDetailBinding
    private lateinit var movie: CineverseModel
    private var selectedDate: String? = null
    private var selectedTime: String? = null
    private var selectedSeats: String? = null
    private var ticketId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCinemaTicketDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        getIntentExtra()
        bottomNavigation()

        binding.nameTxt.text = movie.name
        binding.dateTxt.text = selectedDate
        binding.timeTxt.text = selectedTime
        binding.seatsTxt.text = selectedSeats
        binding.barcodeId.text = ticketId

        val requestOptions = RequestOptions().transform(CenterCrop(), GranularRoundedCorners(80f, 80f, 0f, 0f))
        Glide.with(this)
            .load(movie.image)
            .apply(requestOptions)
            .into(binding.filmPic)

        val barcodeBitmap = if (ticketId != null) {generateBarcode(ticketId!!, width = 300, height = 80)} else {
            null
        }
        binding.barcodePic.apply {
            setImageBitmap(barcodeBitmap)
            scaleType = ImageView.ScaleType.CENTER // Ortalanmış görünüm
            adjustViewBounds = true // Görüntüyü sığdır
        }

    }

    private fun getIntentExtra() {

        movie = intent.getParcelableExtra("seatMovie")!!
        selectedDate = intent.getStringExtra("selectedDate")
        selectedTime = intent.getStringExtra("selectedTime")
        selectedSeats = intent.getStringExtra("seats")
        ticketId = intent.getStringExtra("ticketId")
    }

    private fun generateBarcode(content: String, width: Int, height: Int ): Bitmap {

        val bitMatrix: BitMatrix = MultiFormatWriter()
            .encode(content,BarcodeFormat.CODE_128, width, height)

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x,y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        return  bitmap
    }

    private fun bottomNavigation() {

        binding.chipNavigationBar.setOnItemSelectedListener { id ->
            when(id) {
                R.id.cineverseTicketFragment -> {
                    val intent = Intent (this, CineverseTicketListActivity::class.java)
                    startActivity(intent) }
                R.id.cineverseHomeFragment -> {
                    val intent = Intent (this, CineverseActivity::class.java)
                    startActivity(intent) }
                R.id.movie -> {
                    val intent = Intent ( this, MainActivity::class.java)
                    startActivity(intent) }
                R.id.cineverseSearchFragment -> {

                }
                else  -> {

                }
            }
        }
    }


}
package com.example.movieapp.presentation.ui

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivitySeatListBinding
import com.example.movieapp.domain.model.CineverseModel
import com.example.movieapp.domain.model.CineverseSeatModel
import com.example.movieapp.presentation.adapter.CineverseDateAdapter
import com.example.movieapp.presentation.adapter.CineverseSeatListAdapter
import com.example.movieapp.presentation.adapter.CineverseTimeAdapter
import eightbitlab.com.blurview.RenderScriptBlur
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class SeatListActivity : BaseActivity() {

    private lateinit var binding: ActivitySeatListBinding
    private lateinit var movie: CineverseModel
    private var price: Double = 80.0
    private var number: Int = 0
    private var totalPrice: Double = 0.0
    private var selectedDate: String? = null
    private var selectedTime: String? = null
    private var selectedSeats = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getIntentExtra()
        initSeatsList()

        binding.bookTicketsBtn.setOnClickListener { navigateToSnackAndTicketActivity() }



    }

    private fun initSeatsList() {

        val gridLayoutManager = GridLayoutManager(this, 9)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(position) {


                     3, 11, 19, 27, 35, 43 -> 2

                    // 2, 8, 14, 20 -> 2

                    else -> 1  // Diğer pozisyonlar için varsayılan olarak 1 sütun kapla
                }
            }
        }

        /*
        val gridLayoutManager = GridLayoutManager(this,8)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return if (position % 7 == 3 ) 2
                else 1
            }
        } */

        binding.seatView.layoutManager = gridLayoutManager

        val seatList = mutableListOf<CineverseSeatModel>()
        val numberSeats = 57

        for (i in 0 until numberSeats) {

            val seatName = "S${i + 1}"

            val seatStatus = if (i == 2 || i == 8  || i == 11  || i == 12 || i == 26 || i == 27 ) CineverseSeatModel.SeatStatus.UNAVAILABLE

            else CineverseSeatModel.SeatStatus.AVAILABLE

            seatList.add(CineverseSeatModel(seatStatus, seatName))
        }

        val SeatAdapter = CineverseSeatListAdapter(seatList,this,object : CineverseSeatListAdapter.SelectedSeat {
            override fun Retrun(selectedName: String, num: Int) {
                number = num
                selectedSeats.add(selectedName)
                updatePriceDisplay()
            }
        })

        binding.seatView.adapter = SeatAdapter
        binding.seatView.isNestedScrollingEnabled = false


        binding.datesView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.datesView.adapter = CineverseDateAdapter(generateDates()) { selectedDate ->
            this.selectedDate = selectedDate
        }

        binding.timesView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.timesView.adapter = CineverseTimeAdapter(generateTimeSlots()) {selectedTime ->
            this.selectedTime = selectedTime
        }



        Glide.with(this)
            .load(movie.image)
            .into(binding.filmPic)

        setupBlurView()
    }

    private fun getIntentExtra() {

        movie = intent.getParcelableExtra("seatMovie")!!

    }

    private fun updatePriceDisplay() {
        val df = DecimalFormat("#.##")
        totalPrice = number * price
        val formattedPrice = df.format(totalPrice)
        binding.totalPrice.text = "Total: $formattedPrice"
    }

    private fun navigateToSnackAndTicketActivity(){

        val intent = Intent( this@SeatListActivity, SnackAndTicketActivityActivity::class.java)
        intent.putStringArrayListExtra("selectedSeats", ArrayList(selectedSeats))
        intent.putExtra("totalPrice", totalPrice)
        intent.putExtra("seatListMovie", movie)
        intent.putExtra("seatCount", number)
        intent.putExtra("selectedDate", selectedDate)
        intent.putExtra("selectedTime", selectedTime)


        if (selectedSeats.isEmpty()){
            Toast.makeText(this@SeatListActivity,"Lütfen koltuk seçimi yapınız", Toast.LENGTH_LONG).show()
            return
        }


        if (selectedDate == null || selectedTime == null) {
            Toast.makeText(this@SeatListActivity, "Lütfen bir tarih ve saat seçin",Toast.LENGTH_LONG).show()
            return
        }

        startActivity(intent)
    }


    private fun generateTimeSlots(): List<String> {
        val timeSlots = mutableListOf<String>()
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")

        for (i in 0 until  24 step 2) {
            val time = LocalTime.of(i, 0)
            timeSlots.add(time.format(formatter))
        }
        return timeSlots
    }

    private fun generateDates(): List<String> {
        val dates = mutableListOf<String>()
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEE/dd/MMM")

        for (i in 0 until 7) {
            dates.add(today.plusDays(i.toLong()).format(formatter))
        }
        return dates
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
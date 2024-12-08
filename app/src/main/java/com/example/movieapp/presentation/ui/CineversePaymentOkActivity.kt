package com.example.movieapp.presentation.ui

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.airbnb.lottie.LottieAnimationView
import com.example.movieapp.databinding.ActivityCineversePaymentOkBinding
import com.example.movieapp.domain.model.CineverseModel

class CineversePaymentOkActivity : BaseActivity() {

    private lateinit var binding: ActivityCineversePaymentOkBinding
    private lateinit var animationView: LottieAnimationView
    private lateinit var movie: CineverseModel
    private var totalPrice: Double = 0.0
    private var seatCount: Int = 0
    private var selectedSeats: String? = null
    private var selectedTime: String? = null
    private var selectedDate: String? = null
    private var ticketId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCineversePaymentOkBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getIntentExtra()

        animationView = binding.paymentDoneAnimation
        animationView.playAnimation()
        animationView.addAnimatorListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(p0: Animator) {
                // Animasyon başladığında yapılacak işlemleri buraya ekleyebilirsiniz
            }

            override fun onAnimationEnd(p0: Animator) {
                // Animasyon bittiğinde yapılacak işlem

                putIntentExtra()
                //finish()

            }

            override fun onAnimationCancel(p0: Animator) {
                // Animasyon iptal edildiğinde yapılacak işlem
            }

            override fun onAnimationRepeat(p0: Animator) {
                // Animasyon tekrar ettiğinde yapılacak işlem
            }

        })


    }

    private fun putIntentExtra(){
        val intent = Intent(this@CineversePaymentOkActivity, CinemaTicketDetailActivity::class.java)
        intent.putExtra("seatMovie", movie)
        intent.putExtra("selectedDate", selectedDate)
        intent.putExtra("selectedTime", selectedTime)
        intent.putExtra("seats", selectedSeats)
        intent.putExtra("ticketId", ticketId )

        Handler(Looper.getMainLooper()).postDelayed({ startActivity(intent) }, 1000)
    }

    private fun getIntentExtra(){
        movie = intent.getParcelableExtra("seatListMovie")!!
        totalPrice = intent.getDoubleExtra("totalPrice", 0.0)
        seatCount = intent.getIntExtra("seatCount", 0)
        selectedSeats = intent.getStringArrayListExtra("selectedSeats").toString()
        selectedDate = intent.getStringExtra("selectedDate")
        selectedTime = intent.getStringExtra("selectedTime")
        ticketId = intent.getStringExtra("ticketId")
    }
}
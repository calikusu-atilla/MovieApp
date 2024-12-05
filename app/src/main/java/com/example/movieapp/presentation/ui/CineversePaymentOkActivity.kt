package com.example.movieapp.presentation.ui

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.example.movieapp.databinding.ActivityCineversePaymentOkBinding

class CineversePaymentOkActivity : BaseActivity() {

    private lateinit var binding: ActivityCineversePaymentOkBinding
    private lateinit var animationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCineversePaymentOkBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        animationView = binding.paymentDoneAnimation
        animationView.playAnimation()
        animationView.addAnimatorListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(p0: Animator) {
                // Animasyon başladığında yapılacak işlemleri buraya ekleyebilirsiniz
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(p0: Animator) {

                // Animasyon bittiğinde yapılacak işlem
                // Örneğin, kullanıcıyı başka bir ekrana yönlendirme:
                finish()  // Bu aktiviteyi kapatabiliriz
                TODO("Not yet implemented")
            }

            override fun onAnimationCancel(p0: Animator) {
                // Animasyon iptal edildiğinde yapılacak işlem
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(p0: Animator) {
                // Animasyon tekrar ettiğinde yapılacak işlem
                TODO("Not yet implemented")
            }

        })


    }
}
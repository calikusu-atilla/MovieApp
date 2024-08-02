package com.example.movieapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import com.example.movieapp.databinding.ActivityIntroBinding

class introActivity : BaseActivity() {

    private lateinit var binding : ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.getStartedBtn.setOnClickListener {
            val intent = Intent ( this@introActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}
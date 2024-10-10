package com.example.movieapp.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivitySnackAndTicketActivityBinding

class SnackAndTicketActivityActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySnackAndTicketActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySnackAndTicketActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}
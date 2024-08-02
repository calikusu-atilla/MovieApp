package com.example.movieapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import com.example.movieapp.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.loginBtn.setOnClickListener {
            val intent = Intent (this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }


        binding.registerTxt.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
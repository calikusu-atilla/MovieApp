package com.example.movieapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.movieapp.data.repository.AuthRepository
import com.example.movieapp.data.source.FirebaseAuthManager
import com.example.movieapp.databinding.ActivityIntroBinding
import com.example.movieapp.presentation.viewmodel.AuthViewModel
import com.example.movieapp.presentation.viewmodel.AuthViewModelFactory

class introActivity : BaseActivity() {

    private lateinit var binding : ActivityIntroBinding
    private val authViewModel: AuthViewModel by viewModels { AuthViewModelFactory(AuthRepository(FirebaseAuthManager(this)))  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        CheckCunrentUser()

        binding.getStartedBtn.setOnClickListener {
            val intent = Intent ( this@introActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun CheckCunrentUser() {
        val currentUser = authViewModel.getCurrentUser()
        if (currentUser != null) {
            val intent = Intent(this@introActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
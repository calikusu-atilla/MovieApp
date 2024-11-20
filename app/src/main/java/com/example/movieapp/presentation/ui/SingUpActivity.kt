package com.example.movieapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.movieapp.data.repository.AuthRepository
import com.example.movieapp.data.source.FirebaseAuthManager
import com.example.movieapp.databinding.ActivityRegisterBinding
import com.example.movieapp.presentation.viewmodel.AuthViewModel
import com.example.movieapp.presentation.viewmodel.AuthViewModelFactory

class SingUpActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel : AuthViewModel by viewModels { AuthViewModelFactory(AuthRepository(FirebaseAuthManager(this))) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        binding.loginTxt.setOnClickListener {
            val intent = Intent(this@SingUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        authViewModel.authState.observe(this, Observer{ isAuthenticated ->
            if (isAuthenticated) {
                val  intent = Intent (this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        authViewModel.authError.observe(this, Observer { error ->
            error.let {
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }
        })

        binding.registerBtn.setOnClickListener {
            val  username = binding.usernameTxt.text.toString().trim()
            val  password = binding.passwordTxt.text.toString().trim()
            val  confirmPassword = binding.confirmPasswordTxt.text.toString().trim()
            val  email = binding.emailTxt.text.toString().trim()


            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password == confirmPassword) {
                authViewModel.register(username,email,password,confirmPassword)
            } else {
                Toast.makeText(this,"Lütfen şifreleri kontrol ediniz. Uyuşmuyor.. ", Toast.LENGTH_LONG).show()
            }

        }

    }
}
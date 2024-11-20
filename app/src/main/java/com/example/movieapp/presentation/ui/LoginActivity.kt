package com.example.movieapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.movieapp.data.repository.AuthRepository
import com.example.movieapp.data.source.FirebaseAuthManager
import com.example.movieapp.databinding.ActivityLoginBinding
import com.example.movieapp.presentation.viewmodel.AuthViewModel
import com.example.movieapp.presentation.viewmodel.AuthViewModelFactory

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel : AuthViewModel by viewModels {AuthViewModelFactory(AuthRepository(FirebaseAuthManager(this)))  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

         authViewModel.authState.observe(this, Observer { isAuthenticated ->
            if (isAuthenticated) {
                val intent = Intent (this@LoginActivity,MainActivity::class.java)
                startActivity(intent) //Kullanıcı giriş yaptı bu aşamada
            }else {

            }
        })


        authViewModel.authError.observe(this, Observer { error ->
            error?.let {
                Toast.makeText(this@LoginActivity,"Login Error: $it",Toast.LENGTH_SHORT).show()
            }
        })

        binding.loginBtn.setOnClickListener {
            val email = binding.usernameTxt.text.toString().trim()
            val password = binding.passwordTxt.text.toString().trim()

            // Email ve şifre kontrolü
            if (email.isEmpty()  || password.isEmpty()) {
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            authViewModel.login(email, password)
        }


        binding.registerTxt.setOnClickListener {
            val intent = Intent(this@LoginActivity, SingUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.forgetPasswordTxt.setOnClickListener {
            val intent = Intent(this@LoginActivity,ForgetPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
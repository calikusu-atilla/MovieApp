package com.example.movieapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movieapp.R
import com.example.movieapp.data.repository.AuthRepository
import com.example.movieapp.data.source.FirebaseAuthManager
import com.example.movieapp.databinding.ActivityForgetPasswordBinding
import com.example.movieapp.presentation.viewmodel.AuthViewModel
import com.example.movieapp.presentation.viewmodel.AuthViewModelFactory

class ForgetPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private val authViewModel: AuthViewModel by viewModels { AuthViewModelFactory(AuthRepository(FirebaseAuthManager(this))) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginTxt.setOnClickListener { loginNa() }

        binding.resetPasswordBtn.setOnClickListener {
            val email = binding.usernameTxt.text.toString().trim()
            if (email.isNotEmpty()){
                resetPassword(email)
                Toast.makeText(this@ForgetPasswordActivity,"E-posta adresinizi kontrol ediniz sıfırlama bağlantısı gönderildi.",Toast.LENGTH_LONG).show()
                loginNa()
            }else{
                Toast.makeText(this@ForgetPasswordActivity,"Lütfen geçerli bir e-posta adresi giriniz. ",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loginNa(){
        intent = Intent(this@ForgetPasswordActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun resetPassword(email: String){
        authViewModel.resetPassword(email)
    }
}

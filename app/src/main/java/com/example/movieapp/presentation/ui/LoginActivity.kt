package com.example.movieapp.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.movieapp.R
import com.example.movieapp.data.repository.AuthRepository
import com.example.movieapp.data.source.FirebaseAuthManager
import com.example.movieapp.databinding.ActivityLoginBinding
import com.example.movieapp.presentation.viewmodel.AuthViewModel
import com.example.movieapp.presentation.viewmodel.AuthViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private val authViewModel : AuthViewModel by viewModels {AuthViewModelFactory(AuthRepository(FirebaseAuthManager(this)))  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding.googleRegisterBtn.setOnClickListener {
            val singIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(singIntent)
        }

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

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account : GoogleSignInAccount = task.result
                account?.let {
                    authViewModel.loginWithGoogle(it)
                }
            }catch (e: ApiException) {
                Log.d("GoogleSignIn", "Google Sign-In failed: ${e.message}")
                Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
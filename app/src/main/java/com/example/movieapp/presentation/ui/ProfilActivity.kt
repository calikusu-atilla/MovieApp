package com.example.movieapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.movieapp.data.repository.AuthRepository
import com.example.movieapp.data.source.FirebaseAuthManager
import com.example.movieapp.databinding.ActivityProfilBinding
import com.example.movieapp.presentation.viewmodel.AuthViewModel
import com.example.movieapp.presentation.viewmodel.AuthViewModelFactory



class ProfilActivity : BaseActivity() {

    private lateinit var binding: ActivityProfilBinding
    private val authViewModel: AuthViewModel by viewModels { AuthViewModelFactory(AuthRepository(FirebaseAuthManager(this))) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkCunrentUser()
        bottomNavigation()
        observeAuthState()
    }

    private fun checkCunrentUser() {
        val currentUser = authViewModel.getCurrentUser()
        if (currentUser == null) {
            Log.d("ProfilActivity", "Kullanıcı oturum açmamış.")
            Toast.makeText(this, "Kullanıcı oturum açmamış. Lütfen giriş yapın.", Toast.LENGTH_LONG).show()

            val intent = Intent(this@ProfilActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun logOutUser() {
        Log.d("ProfilActivity", "Oturum kapatılıyor...")
        authViewModel.logout() // Oturum kapatma işlemi başlatılıyor
    }

    private fun observeAuthState() {
        authViewModel.authState.observe(this, Observer { isAuthenticated ->
            if (!isAuthenticated) {
                // Oturum kapatıldı, LoginActivity'ye yönlendir
                Log.d("ProfilActivity", "Kullanıcı oturumu kapatıldı.")
                val intent = Intent(this@ProfilActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun bottomNavigation() {
        binding.profilBtn.setOnClickListener { startActivity(Intent(this@ProfilActivity, ProfilActivity::class.java)) }
        binding.mainBtn.setOnClickListener { startActivity(Intent(this@ProfilActivity, MainActivity::class.java)) }
        binding.logOutBtn.setOnClickListener { logOutUser() } // Oturumu kapatmak için tıklama işlemi
    }
}

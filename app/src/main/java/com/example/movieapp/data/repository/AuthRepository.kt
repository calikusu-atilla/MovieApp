package com.example.movieapp.data.repository

import com.example.movieapp.data.source.FirebaseAuthManager
import com.example.movieapp.domain.repository.AuthRepositoryInterFace
import com.google.firebase.auth.FirebaseUser

class AuthRepository(private val firebaseManager:FirebaseAuthManager) : AuthRepositoryInterFace {

    override fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun resetPassword(email: String, password: String, callback: (Boolean, String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun register(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun isUserLoggedIn(): Boolean {
        TODO("Not yet implemented")
    }
}
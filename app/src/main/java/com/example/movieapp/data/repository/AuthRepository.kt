package com.example.movieapp.data.repository

import com.example.movieapp.data.source.FirebaseAuthManager
import com.example.movieapp.domain.repository.AuthRepositoryInterFace
import com.google.firebase.auth.FirebaseUser

class AuthRepository(private val firebaseAuthManager:FirebaseAuthManager) : AuthRepositoryInterFace {

    override fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuthManager.login(email,password,callback)
    }

    override fun resetPassword(email: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuthManager.resetPassword(email,callback)
    }

    override fun register(userName: String, email: String, password: String, confirmPassword: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuthManager.register(userName, email, password, confirmPassword,  callback)

    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuthManager.getCurrentUser()
    }

    override fun logout() {
        firebaseAuthManager.logout()
    }

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuthManager.getCurrentUser() != null
    }


}
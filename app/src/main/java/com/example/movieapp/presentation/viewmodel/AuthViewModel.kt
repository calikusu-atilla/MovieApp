package com.example.movieapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.repository.AuthRepositoryInterFace
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(private val authRepository: AuthRepositoryInterFace): ViewModel() {

    private val _authState = MutableLiveData<Boolean>()
    val authState: LiveData<Boolean> get() = _authState

    private val _authError = MutableLiveData<String?>()
    val authError: LiveData<String?> get() = _authError

    init {
        _authState.value = authRepository.isUserLoggedIn()
    }


    fun login (email: String, password: String){
        authRepository.login(email,password) { success, error ->
            if (success) {
                _authState.postValue(true)
            }else {
                _authState.postValue(false)
                _authError.postValue(error)
            }
        }
    }


    fun register (userName: String, email: String, password: String, confirmPassword: String){
        authRepository.register(userName,email,password,confirmPassword){ success, error ->
            if (success){
                _authState.postValue(true)
            }else{
                _authError.postValue(error)
            }
        }
    }

    fun logout(){
        authRepository.logout()
        _authState.value = false
    }

    fun resetPassword(email: String) {
        authRepository.resetPassword(email) { success, error ->
            _authState.value = success
            _authError.value = error

        }
    }

    fun getCurrentUser(): FirebaseUser?{
        return authRepository.getCurrentUser()
    }


}

package com.example.movieapp.domain.model

data class UserModel(

    val userName: String ?= null,
    val email: String ?= null,
    val password: String ?= null,
    val confirmPassword: String ?= null
)

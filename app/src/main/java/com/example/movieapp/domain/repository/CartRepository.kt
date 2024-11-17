package com.example.movieapp.domain.repository

import com.example.movieapp.domain.model.CineverseFoodModel

interface CartRepository {

    suspend fun insertItem(item: CineverseFoodModel)
    suspend fun getCartItems(): List<CineverseFoodModel>
    suspend fun removeItem(item: CineverseFoodModel)
    suspend fun incrementItem(item: CineverseFoodModel)
    suspend fun getTotalFee(): Double

}
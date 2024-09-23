package com.example.movieapp.domain.repository

import com.example.movieapp.domain.model.UpcomingMoviesModel

interface UpcomingMoviesRepository {

    // Sayfa numarası ile popüler filmler döndürür
    suspend fun getUpcomingMovies(page: Int): List<UpcomingMoviesModel>
}
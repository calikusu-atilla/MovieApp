package com.example.movieapp.domain.repository

import com.example.movieapp.domain.model.TopMoviesModel

interface TopMoviesRoomRepository {

    suspend fun insertAll(topMovies: List<TopMoviesModel>): List<Long>

    suspend fun getAllTopMovies(): List<TopMoviesModel>

    suspend fun  getTopMovie(topMovieId: Int): TopMoviesModel

    suspend fun deleteTopMovie(topMovieId: Int)

    suspend fun deleteAllTopMovies()
}
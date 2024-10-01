package com.example.movieapp.domain.repository


import com.example.movieapp.domain.model.TrendingMovieModel

interface TredingMoviesRepository {

    suspend fun getTredingMovies(timeWindow: String): List<TrendingMovieModel>

}
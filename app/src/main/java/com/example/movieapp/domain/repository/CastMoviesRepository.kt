package com.example.movieapp.domain.repository

import com.example.movieapp.domain.model.TmdbApiCastModel

interface CastMoviesRepository {

    suspend fun getCastMovies(movieId: Int): List<TmdbApiCastModel>
}
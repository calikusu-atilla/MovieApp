package com.example.movieapp.data.repository

import com.example.movieapp.data.remote.TmdbAPI
import com.example.movieapp.data.remote.dto.toCastModel
import com.example.movieapp.data.remote.dto.toUpcomingMovieDetailModel
import com.example.movieapp.domain.model.TmdbApiCastModel
import com.example.movieapp.domain.model.UpcomingMovieDetailModel
import com.example.movieapp.domain.repository.CastMoviesRepository
import javax.inject.Inject

class CastMoviesRepositoryImpl @Inject constructor( private val api: TmdbAPI): CastMoviesRepository {

    override suspend fun getCastMovies(movieId: Int): List<TmdbApiCastModel> {
        val repository = api.getMovieCast(movieId = movieId)
        return repository.cast.map { it.toCastModel() } // Listeyi dönüştürüyoruz
    }

}
package com.example.movieapp.data.repository

import com.example.movieapp.data.remote.TmdbAPI
import com.example.movieapp.data.remote.dto.toTredingMovioModel
import com.example.movieapp.domain.model.TrendingMovieModel
import com.example.movieapp.domain.repository.TredingMoviesRepository
import javax.inject.Inject

class TredingMoviesRepositoryImpl @Inject constructor(private val api: TmdbAPI): TredingMoviesRepository {

    override suspend fun getTredingMovies(timeWindow: String): List<TrendingMovieModel> {
        val repository = api.getTrendingMovies(timeWindow = timeWindow)
        return repository.toTredingMovioModel()
    }


}
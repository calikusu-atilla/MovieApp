package com.example.movieapp.domain.repository


import com.example.movieapp.domain.model.MovieTmdbApıVideosModel
import com.example.movieapp.domain.model.UpcomingMovieDetailModel
import com.example.movieapp.domain.model.UpcomingMoviesModel

interface UpcomingMoviesRepository {

    // Sayfa numarası ile popüler filmler döndürür
    suspend fun getUpcomingMovies(page: Int): List<UpcomingMoviesModel>

    // Film ID ile film detaylarını döndürür
    suspend fun getUpcomingMoviesDetails(movieId: Int): UpcomingMovieDetailModel

    suspend fun getMoviesVideos(movieId: Int): MovieTmdbApıVideosModel
}
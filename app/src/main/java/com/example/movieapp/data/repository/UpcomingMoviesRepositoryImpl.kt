package com.example.movieapp.data.repository


import com.example.movieapp.data.remote.TmdbAPI
import com.example.movieapp.data.remote.dto.toModel
import com.example.movieapp.data.remote.dto.toUpcomingMoviesList
import com.example.movieapp.domain.model.UpcomingMoviesModel
import com.example.movieapp.domain.repository.UpcomingMoviesRepository
import com.example.movieapp.data.remote.dto.toUpcomingMovieDetailModel
import com.example.movieapp.domain.model.MovieTmdbApıVideosModel
import com.example.movieapp.domain.model.UpcomingMovieDetailModel
import javax.inject.Inject

class UpcomingMoviesRepositoryImpl @Inject constructor(private val api: TmdbAPI): UpcomingMoviesRepository {


    // Sayfaya göre gelecek filmleri döndüren fonksiyon.
    // Bu fonksiyon, API'den gelen UpcomingMoviesDto nesnesini UpcomingMoviesModel listesine dönüştürür.
    override suspend fun getUpcomingMovies(page: Int): List<UpcomingMoviesModel> {
        val response = api.getUpcomingMovies(page = page)
        return response.toUpcomingMoviesList() // DTO'yu domain model listesine dönüştür
    }


    // Belirli bir film ID'sine göre film detaylarını döndüren fonksiyon.
    override suspend fun getUpcomingMoviesDetails(movieId: Int): UpcomingMovieDetailModel {
        val response = api.getUpcomingMoviesDetails(movieId = movieId)
        return response.toUpcomingMovieDetailModel()
    }

    override suspend fun getMoviesVideos(movieId: Int): MovieTmdbApıVideosModel {
        val response = api.getMovieVideos(movieId = movieId)
        return response.toModel()
    }


}
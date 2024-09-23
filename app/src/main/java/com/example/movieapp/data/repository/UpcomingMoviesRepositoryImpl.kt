package com.example.movieapp.data.repository


import com.example.movieapp.data.remote.TmdbAPI
import com.example.movieapp.data.remote.dto.toUpcomingMoviesList
import com.example.movieapp.domain.model.UpcomingMoviesModel
import com.example.movieapp.domain.repository.UpcomingMoviesRepository
import javax.inject.Inject

class UpcomingMoviesRepositoryImpl @Inject constructor(private val api: TmdbAPI): UpcomingMoviesRepository {


    // Sayfaya göre gelecek filmleri döndüren fonksiyon.
    // Bu fonksiyon, API'den gelen UpcomingMoviesDto nesnesini UpcomingMoviesModel listesine dönüştürür.
    override suspend fun getUpcomingMovies(page: Int): List<UpcomingMoviesModel> {
        val response = api.getUpcomingMovies(page = page)
        return response.toUpcomingMoviesList() // DTO'yu domain model listesine dönüştür
    }

}
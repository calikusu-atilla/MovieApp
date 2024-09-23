package com.example.movieapp.data.remote.dto

import com.example.movieapp.domain.model.UpcomingMoviesModel

data class UpcomingMoviesDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

// toUpcomingMoviesList: UpcomingMoviesDto'yu domain modeline dönüştüren genişletme fonksiyonu
fun UpcomingMoviesDto.toUpcomingMoviesList(): List<UpcomingMoviesModel> {
    // Results listesindeki her bir Result nesnesini, UpcomingMovies nesnesine dönüştürerek yeni bir liste oluşturur
    return results.map { result -> UpcomingMoviesModel(
        id = result.id,
        title = result.title,
        overview = result.overview,
        posterPath = result.poster_path,
        releaseDate = result.release_date,
        voteAverage = result.vote_average
    ) }
}
package com.example.movieapp.data.remote.dto

import com.example.movieapp.domain.model.UpcomingMovieDetailModel

data class UpcomingMovieDetailDto(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

// Dönüşüm fonksiyonu
fun UpcomingMovieDetailDto.toUpcomingMovieDetailModel(): UpcomingMovieDetailModel {
    return UpcomingMovieDetailModel(
        uuid = this.id,
        title = this.original_title,
        description = this.overview,
        poster = this.poster_path ?: "",
        time = this.release_date,
        trailer = this.homepage ?: "",
        popularity = (this.popularity ?: "").toString(),
        year = this.release_date.take(4).toIntOrNull() ?: 0,
        genre = this.genres.map { it.name },
    )
}


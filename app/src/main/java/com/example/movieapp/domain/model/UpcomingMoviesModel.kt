package com.example.movieapp.domain.model

data class UpcomingMoviesModel(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double
)


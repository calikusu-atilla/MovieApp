package com.example.movieapp.data.remote.dto

data class TrendingMovieDto(
    val page: Int,
    val results: List<TrendingResult>,
    val total_pages: Int,
    val total_results: Int
)
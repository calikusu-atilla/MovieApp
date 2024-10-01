package com.example.movieapp.data.remote.dto

import com.example.movieapp.domain.model.TrendingMovieModel


data class TrendingMovieDto(
    val page: Int,
    val results: List<TrendingResult>,
    val total_pages: Int,
    val total_results: Int
)

fun TrendingMovieDto.toTredingMovioModel(): List<TrendingMovieModel> {
    return results.map { result-> TrendingMovieModel (
        id = result.id,
        title = result.title,
        overview = result.overview,
        posterPath = result.poster_path,
        releaseDate = result.release_date,
        voteAverage = result.vote_average
    ) }
}

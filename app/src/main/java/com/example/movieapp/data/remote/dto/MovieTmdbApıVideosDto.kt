package com.example.movieapp.data.remote.dto

import com.example.movieapp.domain.model.MovieTmdbApıVideosModel
import com.example.movieapp.domain.model.VideoModel

data class MovieTmdbApıVideosDto(
    val id: Int,
    val results: List<VideosResult>
)

data class VideosResult(
    val id: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val published_at: String,
    val site: String,
    val size: Int,
    val type: String
)


fun MovieTmdbApıVideosDto.toModel(): MovieTmdbApıVideosModel {
    return MovieTmdbApıVideosModel(
        id = this.id,
        videos = this.results.map { result ->
            VideoModel(
                language = result.iso_639_1,
                name = result.name,
                key = result.key,
                site = result.site,
                size = result.size,
                type = result.type
            )
        }
    )
}

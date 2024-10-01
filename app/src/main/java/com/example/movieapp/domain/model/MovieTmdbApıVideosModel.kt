package com.example.movieapp.domain.model

data class MovieTmdbApıVideosModel(

    val id: Int,
    val videos: List<VideoModel>
)

data class VideoModel(
    val language: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String
)



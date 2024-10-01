package com.example.movieapp.data.remote.dto

import com.example.movieapp.domain.model.TmdbApiCastModel

data class CastMoviesDto(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)

fun Cast.toCastModel(): TmdbApiCastModel {
    return TmdbApiCastModel(
        id = this.id,
        PicUrl = this.profile_path,   //?.let { "https://image.tmdb.org/t/p/w500$it" }, // Resim URL'sini oluştur
        Aktor = this.name
    )
}

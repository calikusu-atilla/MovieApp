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
        PicUrl = this.profile_path ?: "",
        Aktor = this.name
    )
}

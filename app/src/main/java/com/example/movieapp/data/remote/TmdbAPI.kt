package com.example.movieapp.data.remote

import com.example.movieapp.data.remote.dto.UpcomingMoviesDto
import com.example.movieapp.util.Constants.API_KEY_TMDB
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbAPI {

    //https://api.themoviedb.org/3/movie/popular
    //https://api.themoviedb.org/3/movie/popular?language=en-US&page=1

    // URL'deki sayfa numarasını alır ve API'ye istek gönderir.
    @GET("movie/popular")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY_TMDB,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): UpcomingMoviesDto
}
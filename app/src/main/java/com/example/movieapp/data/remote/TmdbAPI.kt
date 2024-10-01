package com.example.movieapp.data.remote

import com.example.movieapp.data.remote.dto.CastMoviesDto
import com.example.movieapp.data.remote.dto.MovieTmdbApıVideosDto
import com.example.movieapp.data.remote.dto.TrendingMovieDto
import com.example.movieapp.data.remote.dto.UpcomingMovieDetailDto
import com.example.movieapp.data.remote.dto.UpcomingMoviesDto
import com.example.movieapp.util.Constants.API_KEY_TMDB
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {


    //https://api.themoviedb.org/3/movie/popular?api_key={""}&language=en-US&page=1

    // URL'deki sayfa numarasını alır ve API'ye istek gönderir.
    @GET("movie/popular")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY_TMDB,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): UpcomingMoviesDto



    //Detay
    //https://api.themoviedb.org/3/movie/movie_id?api_key={""}&language=en-US"
    //https://api.themoviedb.org/3/movie/550?api_key=8a93f57e30c1d5c501231d861a8f70ae&language=en-US

    @GET("movie/{movie_id}")
    suspend fun getUpcomingMoviesDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY_TMDB,
        @Query("language") language: String = "en-US"
    ): UpcomingMovieDetailDto



    //https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key={""}&language=en-US
    //https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=8a93f57e30c1d5c501231d861a8f70ae&language=en-US

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY_TMDB,
        @Query("language") language: String = "en-US"
    ): MovieTmdbApıVideosDto


    //https://api.themoviedb.org/3/trending/movie/{time_window}?api_key={""}&language=en-US  {time_window} --> "day" yada "week"
    //https://api.themoviedb.org/3/trending/movie/day?api_key=8a93f57e30c1d5c501231d861a8f70ae&language=en-US

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey: String = API_KEY_TMDB,
        @Query("language") language: String = "en-US"
    ): TrendingMovieDto


    //https://api.themoviedb.org/3/movie/movie_id/credits?api_key={""}&language=en-US
    //https://api.themoviedb.org/3/movie/550/credits?api_key=8a93f57e30c1d5c501231d861a8f70ae&language=en-US

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY_TMDB,
        @Query("language") language: String = "en-US"
    ): CastMoviesDto


}
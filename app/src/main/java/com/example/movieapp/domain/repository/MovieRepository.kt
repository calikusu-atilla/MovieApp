package com.example.movieapp.domain.repository

import com.example.movieapp.data.remote.dto.MovieDetailDto
import com.example.movieapp.data.remote.dto.MoviesDto
import com.example.movieapp.domain.model.UpcomingMoviesModel

// MovieRepository arayüzü, film verilerini sağlamak için kullanılan metotları tanımlar.
interface MovieRepository {

    // Belirtilen arama terimine göre film listesini döndüren bir fonksiyon.
    // Bu fonksiyon, arama terimi (search) alır ve MoviesDto nesnesini geri döner.
    suspend fun getMovies(search : String) : MoviesDto


    // Belirtilen IMDb ID'sine göre film detaylarını döndüren bir fonksiyon.
    // Bu fonksiyon, IMDb ID'sini (imdbId) alır ve MovieDetailDto nesnesini geri döner.
    suspend fun getMovieDetail( imdbId : String) : MovieDetailDto


}
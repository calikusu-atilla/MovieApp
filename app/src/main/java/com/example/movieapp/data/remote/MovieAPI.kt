package com.example.movieapp.data.remote

import com.example.movieapp.data.remote.dto.MovieDetailDto
import com.example.movieapp.data.remote.dto.MoviesDto
import com.example.movieapp.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    /*  Retrofit kullanarak bir HTTP API'sine istek gönderiyorum ve gelen yanıtı belirli bir veri modeliyle eşleştirmek için  */

    //http://www.omdbapi.com/?i=tt0372784&apikey=c0e706d2
    //http://www.omdbapi.com/?s=batman&apikey=c0e706d2

    @GET(".")
    suspend fun getMovies(   //Coroutine içinde çalıştırmak üzere suspend fonksiyon oluşturuldu arka asenkron çalıştırılıyor.
        @Query("s") searchString : String,   //URL'ye s parametresini ekler ve kullanıcı tarafından sağlanan searchString değerini bu parametreye atar.
        @Query("apikey") apiKey : String = API_KEY
    ) : MoviesDto   // Yapılan get isteği sonucunda dönen yanıt MoviesDto aktarılır

    @GET(".")
    suspend fun getMovieDetail(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String = API_KEY
    ) : MovieDetailDto

}

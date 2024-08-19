package com.example.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapp.domain.model.TopMoviesModel

@Dao
interface TopMoviesDao {

    @Insert
    suspend fun insertAll(vararg topMoviesModel: TopMoviesModel) : List<Long>


    @Query ("SELECT * FROM top_movies")
    suspend fun getAllTopMovies() : List<TopMoviesModel>

    @Query ("SELECT * FROM top_movies WHERE uuid = :topMovieId")
    suspend fun getTopMovie(topMovieId: Int ) : TopMoviesModel

    @Query ("DELETE  FROM top_movies WHERE uuid = :topMovieId")
    suspend fun  deleteTopMovie(topMovieId: Int) : TopMoviesModel


}
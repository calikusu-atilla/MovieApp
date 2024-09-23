package com.example.movieapp.data.repository

import com.example.movieapp.data.local.dao.TopMoviesDao
import com.example.movieapp.domain.model.TopMoviesModel
import com.example.movieapp.domain.repository.TopMoviesRoomRepository

class TopMoviesRoomRepositoryImpl (private val topMoviesDao: TopMoviesDao) : TopMoviesRoomRepository {

    override suspend fun insertAll(topMovies: List<TopMoviesModel>): List<Long> {
       return topMoviesDao.insertAll(*topMovies.toTypedArray())
    }

    override suspend fun getAllTopMovies(): List<TopMoviesModel> {
        return topMoviesDao.getAllTopMovies()
    }

    override suspend fun getTopMovie(topMovieId: Int): TopMoviesModel {
        return topMoviesDao.getTopMovie(topMovieId)
    }

    override suspend fun deleteTopMovie(topMovieId: Int) {
        topMoviesDao.deleteTopMovie(topMovieId) }

    override suspend fun deleteAllTopMovies() {
        return topMoviesDao.deleteAllTopMovie()
    }
}

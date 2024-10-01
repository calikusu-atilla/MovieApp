package com.example.movieapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.MovieTmdbApıVideosModel
import com.example.movieapp.domain.model.UpcomingMovieDetailModel
import com.example.movieapp.domain.repository.UpcomingMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(application: Application, override val upcomingMoviesRepository: UpcomingMoviesRepository): BaseViewModel(application){

    private val _movieVideo = MutableLiveData<MovieTmdbApıVideosModel?>()
    val movieVideo: MutableLiveData<MovieTmdbApıVideosModel?> = _movieVideo

    private val _movieDetails = MutableLiveData<UpcomingMovieDetailModel>()
    val movieDetails: LiveData<UpcomingMovieDetailModel>  = _movieDetails


    fun getMoviesVideos(movieId: Int){
        viewModelScope.launch {
            try {
                val videos = upcomingMoviesRepository.getMoviesVideos(movieId)
                if (videos != null) {
                    _movieVideo.postValue(videos)
                }else {
                    Log.d("DetailViewModel", "Video bulunamadı")
                    // Boş veri durumu yönetimi
                }

            }catch (e: Exception){
                Log.e("DetailViewModel", "Error loading movies Videos", e)
                e.printStackTrace()
            }
        }
    }

    fun getMovieDetails(movieId : Int){
        viewModelScope.launch {
            try {
                val details = upcomingMoviesRepository.getUpcomingMoviesDetails(movieId)
                Log.d("DetailViewModel", "Apı referansı alındı: $details")
                if (details != null) {
                    _movieDetails.postValue(details)
                }else {
                    Log.d("DetailViewModel", "Film bulunamadı")
                    // Boş veri durumu yönetimi
                }

            }catch (e: Exception) {
                Log.e("DetailViewModel", "Error loading movies", e)
                e.printStackTrace()
            }
        }
    }


}
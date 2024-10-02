package com.example.movieapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.MovieTmdbApıVideosModel
import com.example.movieapp.domain.model.TmdbApiCastModel
import com.example.movieapp.domain.model.UpcomingMovieDetailModel
import com.example.movieapp.domain.repository.CastMoviesRepository
import com.example.movieapp.domain.repository.UpcomingMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(application: Application, override val upcomingMoviesRepository: UpcomingMoviesRepository,  val castMoviesRepository: CastMoviesRepository): BaseViewModel(application){

    private val _movieVideo = MutableLiveData<MovieTmdbApıVideosModel?>()
    val movieVideo: MutableLiveData<MovieTmdbApıVideosModel?> = _movieVideo

    private val _movieDetails = MutableLiveData<UpcomingMovieDetailModel>()
    val movieDetails: LiveData<UpcomingMovieDetailModel>  = _movieDetails

    private val _castMovies = MutableLiveData<List<TmdbApiCastModel?>>()
    val castMovies : LiveData<List<TmdbApiCastModel?>> = _castMovies


    fun getCastMovies(movieId: Int){
        viewModelScope.launch {
            try {
                val cast = castMoviesRepository.getCastMovies(movieId)
                if (cast.isNotEmpty())  {
                    _castMovies.postValue(cast)
                }else {
                    Log.d("DetailViewModel", "Cast bulunamadı")
                }

            }catch (e: Exception){
                Log.e("DetailViewModel", "Error loading cast", e)
                e.printStackTrace()
            }
        }
    }

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
package com.example.movieapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.UpcomingMovieDetailModel
import com.example.movieapp.domain.repository.UpcomingMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(application: Application, override val repository: UpcomingMoviesRepository): BaseViewModel(application){

    private val _movieDetails = MutableLiveData<UpcomingMovieDetailModel>()
    val movieDetails: LiveData<UpcomingMovieDetailModel>  = _movieDetails

    fun getMovieDetails(movieId : Int){
        viewModelScope.launch {
            try {
                val details = repository.getUpcomingMoviesDetails(movieId)
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
package com.example.movieapp.domain.model

data class CineverseSeatModel(var status: SeatStatus, var name: String) {

     enum class SeatStatus{

         AVAILABLE,SELECTED,UNAVAILABLE

     }
}

package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable

data class TrendingMovieModel(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(overview)
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeDouble(voteAverage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TrendingMovieModel> {
        override fun createFromParcel(parcel: Parcel): TrendingMovieModel {
            return TrendingMovieModel(parcel)
        }

        override fun newArray(size: Int): Array<TrendingMovieModel?> {
            return arrayOfNulls(size)
        }
    }
}
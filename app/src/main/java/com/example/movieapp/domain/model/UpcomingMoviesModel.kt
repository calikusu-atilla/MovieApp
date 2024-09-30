package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable

data class UpcomingMoviesModel(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double
): Parcelable {
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

    companion object CREATOR : Parcelable.Creator<UpcomingMoviesModel> {
        override fun createFromParcel(parcel: Parcel): UpcomingMoviesModel {
            return UpcomingMoviesModel(parcel)
        }

        override fun newArray(size: Int): Array<UpcomingMoviesModel?> {
            return arrayOfNulls(size)
        }
    }
}


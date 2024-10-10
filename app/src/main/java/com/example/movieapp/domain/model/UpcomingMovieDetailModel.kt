package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable

data class UpcomingMovieDetailModel(

    var uuid: Int,
    var title: String,
    var description: String,
    var poster: String,
    var time: String,
    var trailer: String,
    var popularity: String,
    var year: Int,
    var genre: List<String>,
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.createStringArrayList() ?: emptyList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(uuid)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(poster)
        parcel.writeString(time)
        parcel.writeString(trailer)
        parcel.writeString(popularity)
        parcel.writeInt(year)
        parcel.writeStringList(genre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UpcomingMovieDetailModel> {
        override fun createFromParcel(parcel: Parcel): UpcomingMovieDetailModel {
            return UpcomingMovieDetailModel(parcel)
        }

        override fun newArray(size: Int): Array<UpcomingMovieDetailModel?> {
            return arrayOfNulls(size)
        }
    }
}

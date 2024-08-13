package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class TopMoviesModel (
    var title: String = "",
    var description: String = "",
    var poster: String = "",
    var time: String = "",
    var trailer: String = "",
    var Imdb: Int = 0,
    var year: Int = 0,
    var genre: ArrayList<String> = ArrayList(),
    //var casts: ArrayList<String> = ArrayList(),
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.createStringArrayList() as ArrayList<String>,
        //parcel.createStringArrayList() as ArrayList<String>
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(poster)
        parcel.writeString(time)
        parcel.writeString(trailer)
        parcel.writeInt(Imdb)
        parcel.writeInt(year)
        parcel.writeStringList(genre)
       // parcel.writeStringList(casts)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TopMoviesModel> {
        override fun createFromParcel(parcel: Parcel): TopMoviesModel {
            return TopMoviesModel(parcel)
        }

        override fun newArray(size: Int): Array<TopMoviesModel?> {
            return arrayOfNulls(size)
        }
    }
}
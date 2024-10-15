package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable

data class CineverseModel(
    var image: String = "",
    var name: String = "",
    var genre: String = "",
    var age: String = "",
    var year: String = "",
    var time: String = "",
    var Imdb: String = "",
    var trailer: String = "",
    var description: String ="",
    var casts: ArrayList<CineverseCastModel> = ArrayList(),
    var reviews: ArrayList<CineverseReviewsModel> = ArrayList(),
    var recommended: ArrayList<CineverseRecommendedModel> = ArrayList()

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createTypedArrayList(CineverseCastModel.CREATOR) ?: ArrayList(),
        parcel.createTypedArrayList(CineverseReviewsModel.CREATOR) ?: ArrayList(),
        parcel.createTypedArrayList(CineverseRecommendedModel.CREATOR) ?: ArrayList(),
    ) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(genre)
        parcel.writeString(age)
        parcel.writeString(year)
        parcel.writeString(time)
        parcel.writeString(Imdb)
        parcel.writeString(trailer)
        parcel.writeString(description)
        parcel.writeTypedList(casts)
        parcel.writeTypedList(reviews)
        parcel.writeTypedList(recommended)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CineverseModel> {
        override fun createFromParcel(parcel: Parcel): CineverseModel {
            return CineverseModel(parcel)
        }

        override fun newArray(size: Int): Array<CineverseModel?> {
            return arrayOfNulls(size)
        }
    }

}

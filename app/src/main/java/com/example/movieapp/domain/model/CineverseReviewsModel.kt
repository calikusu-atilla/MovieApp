package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable

data class CineverseReviewsModel(
    var review : String = "",
    var username : String = "",
    var userscore : String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(review)
        parcel.writeString(username)
        parcel.writeString(userscore)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CineverseReviewsModel> {
        override fun createFromParcel(parcel: Parcel): CineverseReviewsModel {
            return CineverseReviewsModel(parcel)
        }

        override fun newArray(size: Int): Array<CineverseReviewsModel?> {
            return arrayOfNulls(size)
        }
    }
}

package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable

data class CineverseRecommendedModel(
    var picUrl : String = "",
    var name : String = ""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(picUrl)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CineverseRecommendedModel> {
        override fun createFromParcel(parcel: Parcel): CineverseRecommendedModel {
            return CineverseRecommendedModel(parcel)
        }

        override fun newArray(size: Int): Array<CineverseRecommendedModel?> {
            return arrayOfNulls(size)
        }
    }
}

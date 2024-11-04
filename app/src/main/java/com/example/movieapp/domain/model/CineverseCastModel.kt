package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable

data class CineverseCastModel(
    var PicUrl: String = "",
    var Actor: String = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
    ) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(PicUrl)
        parcel.writeString(Actor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CineverseCastModel> {
        override fun createFromParcel(parcel: Parcel): CineverseCastModel {
            return CineverseCastModel(parcel)
        }

        override fun newArray(size: Int): Array<CineverseCastModel?> {
            return arrayOfNulls(size)
        }
    }
}
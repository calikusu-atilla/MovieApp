package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable

data class CineverseFoodModel(
    var foodName: String = "",
    var foodPrice: Double = 0.0,
    var foodPic: String = "",
    var quantity: Int = 1

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(foodName)
        parcel.writeDouble(foodPrice)
        parcel.writeString(foodPic)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CineverseFoodModel> {
        override fun createFromParcel(parcel: Parcel): CineverseFoodModel {
            return CineverseFoodModel(parcel)
        }

        override fun newArray(size: Int): Array<CineverseFoodModel?> {
            return arrayOfNulls(size)
        }
    }
}
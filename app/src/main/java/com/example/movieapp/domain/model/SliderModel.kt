package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class SliderModel(
    var image: String = "",
    var name: String = "",
    var genre: String = "",
    var age: String = "",
    var year: String = "",
    var time: String = ""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(genre)
        parcel.writeString(age)
        parcel.writeString(year)
        parcel.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SliderModel> {
        override fun createFromParcel(parcel: Parcel): SliderModel {
            return SliderModel(parcel)
        }

        override fun newArray(size: Int): Array<SliderModel?> {
            return arrayOfNulls(size)
        }
    }
}

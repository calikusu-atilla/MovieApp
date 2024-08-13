package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CastModel(
    var PicUrl: String = "",
    var Aktor: String = "",
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(PicUrl)
        parcel.writeString(Aktor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CastModel> {
        override fun createFromParcel(parcel: Parcel): CastModel {
            return CastModel(parcel)
        }

        override fun newArray(size: Int): Array<CastModel?> {
            return arrayOfNulls(size)
        }
    }
}

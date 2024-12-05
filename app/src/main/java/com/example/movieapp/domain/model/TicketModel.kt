package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable


data class TicketModel (
    var movieName: String? = null,
    var movieScreen: String? = null,
    var date: String? = null,
    var time: String? = null,
    var seats: String? = null,
    var totalPrice: Double? = null,
    var picUrl: String? = null

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(movieName)
        parcel.writeString(movieScreen)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(seats)
        parcel.writeValue(totalPrice)
        parcel.writeString(picUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TicketModel> {
        override fun createFromParcel(parcel: Parcel): TicketModel {
            return TicketModel(parcel)
        }

        override fun newArray(size: Int): Array<TicketModel?> {
            return arrayOfNulls(size)
        }
    }
}
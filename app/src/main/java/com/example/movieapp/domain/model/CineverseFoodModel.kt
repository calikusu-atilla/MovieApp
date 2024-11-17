package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartItems")
data class CineverseFoodModel(
    @PrimaryKey val id: Int? = null,
    var foodName: String = "",
    var foodPrice: Double = 0.0,
    var foodPic: String = "",
    var quantity: Int =1 ,

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(foodName)
        parcel.writeDouble(foodPrice)
        parcel.writeString(foodPic)
        parcel.writeInt(quantity)
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
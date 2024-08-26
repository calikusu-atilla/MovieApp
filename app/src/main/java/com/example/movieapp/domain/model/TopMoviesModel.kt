package com.example.movieapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.movieapp.data.local.converters.GenreTypeConverter

@Entity(tableName = "top_movies")
data class TopMoviesModel (


    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0,

    @ColumnInfo (name = "title")
    var title: String = "",

    @ColumnInfo (name = "description")
    var description: String = "",

    @ColumnInfo (name = "poster")
    var poster: String = "",

    @ColumnInfo (name = "time")
    var time: String = "",

    @ColumnInfo (name = "trailer")
    var trailer: String = "",

    @ColumnInfo (name = "imdb")
    var Imdb: Int = 0,

    @ColumnInfo (name = "year")
    var year: Int = 0,

    @ColumnInfo (name = "genre")
    @TypeConverters(GenreTypeConverter::class)
    var genre: ArrayList<String> = ArrayList(),

    //var casts: ArrayList<String> = ArrayList(),
):Parcelable {
    constructor(parcel: Parcel) : this(
        uuid = parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.createStringArrayList() ?: ArrayList()

        //parcel.createStringArrayList() as ArrayList<String>,
        //parcel.createStringArrayList() as ArrayList<String>
    ) {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(uuid)
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
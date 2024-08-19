package com.example.movieapp.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreTypeConverter {

    @TypeConverter
    fun fromGenreList(genre: ArrayList<String>): String {
        return Gson().toJson(genre)
    }

    @TypeConverter
    fun toGenreList(genreString: String): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(genreString, listType)
    }

}
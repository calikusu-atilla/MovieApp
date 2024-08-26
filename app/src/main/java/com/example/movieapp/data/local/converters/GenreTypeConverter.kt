package com.example.movieapp.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreTypeConverter {

    private val gson = Gson()
    private val listType = object : TypeToken<ArrayList<String>>() {}.type

    @TypeConverter
    fun fromGenreList(genre: ArrayList<String>): String {
        return gson.toJson(genre)
    }

    @TypeConverter
    fun toGenreList(genreString: String): ArrayList<String> {
        return try {
            gson.fromJson(genreString, listType)
        } catch (e: Exception) {
            ArrayList() // Hata durumunda boş bir liste döndür
        }
    }
}

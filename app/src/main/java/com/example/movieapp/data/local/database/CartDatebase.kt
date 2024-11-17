package com.example.movieapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.local.dao.CartDao
import com.example.movieapp.domain.model.CineverseFoodModel

@Database(entities = [CineverseFoodModel::class], version = 5)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

}
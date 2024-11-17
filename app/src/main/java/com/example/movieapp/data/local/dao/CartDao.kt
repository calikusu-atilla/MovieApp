package com.example.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieapp.domain.model.CineverseFoodModel

@Dao
interface CartDao {

    @Insert
    suspend fun insertItem(item: CineverseFoodModel)

    @Query("SELECT * FROM cartItems")
    suspend fun getCartItems(): List<CineverseFoodModel>

    @Delete
    suspend fun deleteItem(item: CineverseFoodModel)

    @Update
    suspend fun updateItem(item: CineverseFoodModel)
    
}
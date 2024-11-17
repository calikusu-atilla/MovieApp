package com.example.movieapp.data.repository

import com.example.movieapp.data.local.dao.CartDao
import com.example.movieapp.domain.model.CineverseFoodModel
import com.example.movieapp.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartDao: CartDao) : CartRepository {

    private val itemQuantities = mutableMapOf<Long, Int>()  // Burada tür belirtmemiz gerekiyor

    // Ürünü sepete eklemek
    override suspend fun insertItem(item: CineverseFoodModel) {
        cartDao.insertItem(item)
        // Sepete eklenen ürünün miktarını 1 olarak başlatıyoruz
        itemQuantities[item.id!!.toLong()] = 1
    }

    // Sepetteki ürünleri almak
    override suspend fun getCartItems(): List<CineverseFoodModel> {
        return cartDao.getCartItems()
    }

    // Ürün sayısını azaltmak veya ürün silmek
    override suspend fun removeItem(item: CineverseFoodModel) {
        val quantity = itemQuantities[item.id!!.toLong()] ?: return

        if (quantity <= 1) {
            cartDao.deleteItem(item)
            itemQuantities.remove(item.id!!.toLong())
        } else {
            itemQuantities[item.id!!.toLong()] = quantity - 1
            cartDao.updateItem(item)
        }
    }

    // Ürün sayısını artırmak
    override suspend fun incrementItem(item: CineverseFoodModel) {
        val quantity = itemQuantities[item.id!!.toLong()] ?: return
        itemQuantities[item.id!!.toLong()] = quantity + 1
        cartDao.updateItem(item)
    }

    // Toplam ücreti hesaplamak
    override suspend fun getTotalFee(): Double {
        return getCartItems().sumOf { item ->
            (itemQuantities[item.id!!.toLong()] ?: 0) * item.foodPrice
        }
    }
}
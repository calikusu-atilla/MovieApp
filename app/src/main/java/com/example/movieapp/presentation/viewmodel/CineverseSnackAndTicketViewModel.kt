package com.example.movieapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.model.CineverseFoodModel
import com.example.movieapp.domain.model.TicketModel
import com.example.movieapp.domain.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CineverseSnackAndTicketViewModel @Inject constructor(private val firebaseDatabase: FirebaseDatabase, private val firebaseDatabaseReference: DatabaseReference) : ViewModel() {

    private val _foodlist = MutableLiveData<MutableList<CineverseFoodModel>>()
    val foodlist: LiveData<MutableList<CineverseFoodModel>> = _foodlist

    private val _selectedSnacks = MutableLiveData<MutableList<CineverseFoodModel>>(mutableListOf())
    val selectedSnacks: LiveData<MutableList<CineverseFoodModel>> = _selectedSnacks

    private val _snacksListTotalPrice = MutableLiveData<Double>()
    val snacksListTotalPrice: LiveData<Double> get() = _snacksListTotalPrice

    private val _ticketId = MutableLiveData<String?>()
    val ticketId: LiveData<String?> get() = _ticketId


    fun ticketSave (movie: String, movieScreen: String, date: String, time: String, seats: String, totalPrice: Double, picUrl: String) {

        val ticketList = TicketModel(movie, movieScreen, date, time, seats, totalPrice, picUrl)
        val ticketRef = firebaseDatabaseReference.child("TicketList").push()
        ticketRef.setValue(ticketList).addOnSuccessListener {
            val id = ticketRef.key
            if (id != null) {
                _ticketId.postValue(id)
            }
        }
            .addOnFailureListener { error ->
                Log.e("FirebaseError", "Failed to save ticket: ${error.message}")
            }
    }

    // Firebase'den "Foodlist" referansına giderek yemek listesini yükler.
    fun loadFoodList() {
        val ref = firebaseDatabase.getReference("Foodlist")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CineverseFoodModel>()
                for (child in snapshot.children) {
                    val data = child.getValue(CineverseFoodModel::class.java)
                    if (data != null) {
                        list.add(data)
                    }
                }
                _foodlist.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Data load cancelled: ${error.message}")
            }
        })
    }


    fun updateSnackQuantity(snack: CineverseFoodModel, newQuantity: Int) {

        val currentList = _selectedSnacks.value ?: mutableListOf()
        val index = currentList.indexOfFirst { it.foodName == snack.foodName }
        if (index != -1) {
            currentList[index].quantity = newQuantity
        }else {
            snack.quantity = newQuantity
            currentList.add(snack)
        }
        _selectedSnacks.value = currentList
    }

    fun removeSnack (snack: CineverseFoodModel) {
        val currentList = _selectedSnacks.value ?: mutableListOf() ?: mutableListOf()
        currentList.remove(snack)
        _selectedSnacks.value = currentList
    }

    fun snackListTotalPrice (price: Double) {

        _snacksListTotalPrice.value = price
    }

    fun totalAmount (tAmount: Double) {
        val tAmount = (_snacksListTotalPrice.value ?: 0.0)
    }

}
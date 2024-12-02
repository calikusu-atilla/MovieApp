package com.example.movieapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.model.CineverseFoodModel
import com.example.movieapp.domain.model.TicketModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketListViewModel @Inject constructor(private val firebaseDatabase: FirebaseDatabase): ViewModel(){

    private val _ticketlist = MutableLiveData<MutableList<TicketModel>>()
    val ticketlist: LiveData <MutableList<TicketModel>> = _ticketlist

    fun loadTicketList() {
        val ref = firebaseDatabase.getReference("TicketList")
        Log.d( "TicketListViewModel","Tickets referansı alındı: $ref")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<TicketModel>()
                for (child in snapshot.children) {
                    val list = child.getValue(TicketModel::class.java)
                    if (list != null ) {
                        lists.add(list!!)
                    }
                }
                _ticketlist.value = lists
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("TicketListViewModel", "Ticket Firebase hatası: ${error.message}")
            }
        } )
    }
}

package com.example.movieapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.model.CineverseFoodModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CineverseSnackAndTicketViewModel @Inject constructor(private val firebaseDatabase: FirebaseDatabase): ViewModel(){


    private val _foodlist = MutableLiveData<MutableList<CineverseFoodModel>>()
    val foodlist : LiveData<MutableList<CineverseFoodModel>> = _foodlist

    private val _selectedSnacks = MutableLiveData<MutableList<CineverseFoodModel>>(mutableListOf())
    val selectedSnacks: LiveData<MutableList<CineverseFoodModel>> get() = _selectedSnacks

    fun loadFoodList(){
        val Ref = firebaseDatabase.getReference("Foodlist")
        Ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CineverseFoodModel>()
                for (child in snapshot.children){
                    val data = child.getValue(CineverseFoodModel::class.java)
                    if (data!=null){
                        list.add(data)
                    }
                }
                _foodlist.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun addSelectedSnack(foodItem: CineverseFoodModel) {
        val updatedList = _selectedSnacks.value?.toMutableList() ?: mutableListOf()
        updatedList.add(foodItem)
        _selectedSnacks.value = updatedList
    }

}
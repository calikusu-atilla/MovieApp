package com.example.movieapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.model.CineverseModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CineverseViewModel @Inject constructor( private val firebaseDatabase: FirebaseDatabase) : ViewModel() {


    private val _banner = MutableLiveData<List<CineverseModel>>()
    val banners : LiveData <List<CineverseModel>> = _banner


    fun loadCineverseBanner(){
        val Ref = firebaseDatabase.getReference("Cineverse")
        Log.d( "CineverseViewModel","Cineverse referansı alındı: $Ref")
        Ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CineverseModel>()
                for (child in snapshot.children){
                    val list = child.getValue(CineverseModel::class.java)
                    if (list != null){
                        lists.add(list!!)
                    }else{

                    }
                }
                _banner.value = lists
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("CineverseViewModel", "Cineverse Firebase hatası: ${error.message}")
            }

        })
    }

}

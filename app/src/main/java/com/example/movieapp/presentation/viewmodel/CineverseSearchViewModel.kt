package com.example.movieapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.model.CineverseModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CineverseSearchViewModel @Inject constructor(private val firebaseDatabase: FirebaseDatabase, private val firebaseDatabaseReference: DatabaseReference): ViewModel() {

    private val _searchResults = MutableLiveData<List<CineverseModel>>()
    val searchResult: LiveData<List<CineverseModel>> = _searchResults

    fun searchMovies(query: String = "") {
        val ref = firebaseDatabase.getReference("Cineverse")
        Log.d("CineverseSearchViewModel","CineverseSearchViewModel referansı alındı: $ref")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val resultlist = mutableListOf<CineverseModel>()
                for (child in snapshot.children) {
                    val movie = child.getValue(CineverseModel::class.java)
                    if (movie !=null) {
                        if (query.isEmpty() || movie.name.contains(query, ignoreCase = true)) {
                            resultlist.add(movie)
                        }
                    }
                }
                _searchResults.value = resultlist
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CineverseSearchViewModel", "CineverseSearchViewModel Firebase hatası: ${error.message}")
                _searchResults.value = emptyList()
            }
        })

    }
}
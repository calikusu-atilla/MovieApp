package com.example.movieapp.presentation.viewmodel

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.model.TicketModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TicketListViewModel @Inject constructor(private val firebaseDatabase: FirebaseDatabase): ViewModel(){

    // Süresi Dolmamış biletler
    private val _upcomingTickets = MutableLiveData<MutableList<TicketModel>>()
    val upcomingTickets: LiveData <MutableList<TicketModel>> = _upcomingTickets

    //Süresi Dolmuş biletler
    private val _expiredTickets = MutableLiveData<MutableList<TicketModel>>()
    val expiredTickets: LiveData <MutableList<TicketModel>> = _expiredTickets


    fun expiredTickets() {
        val ref = firebaseDatabase.getReference("TicketList")
        Log.d("TicketsListViewModel", "Tickets referansı alındı: $ref" )

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<TicketModel>()
                val currentDateTime = System.currentTimeMillis() // Sistem Saat ve zamanı
                for (chilp in snapshot.children) {
                    val ticket = chilp.getValue(TicketModel::class.java)
                    if (ticket != null) {
                        val ticketDateTime = parseDateTime(ticket.date, ticket.time)
                        if (ticketDateTime != null && ticketDateTime <= currentDateTime) {
                            lists.add(ticket)
                        }
                    }
                }
                _expiredTickets.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TicketListViewModel", "Ticket Firebase hatası: ${error.message}")
            }
        })
    }

    fun upcomingTickets() {
        val ref = firebaseDatabase.getReference("TicketList")
        Log.d( "TicketsListViewModel","Tickets referansı alındı: $ref")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<TicketModel>()
                val currentDataTime = System.currentTimeMillis() // Sistem Saat ve zamanı
                 for (child in snapshot.children) {
                    val ticket = child.getValue(TicketModel::class.java)
                    if (ticket != null ) {
                        val ticketDateTime = parseDateTime(ticket.date, ticket.time)
                        if (ticketDateTime != null && ticketDateTime >= currentDataTime) {
                            lists.add(ticket)
                        }else {
                            Log.w("TicketListViewModel", "Eski tarihli bilet atlandı: $ticket")
                        }
                    }else {
                        Log.w("TicketListViewModel", "Geçersiz TicketModel verisi: $child")
                    }
                }
                _upcomingTickets.value = lists
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("TicketListViewModel", "Ticket Firebase hatası: ${error.message}")
            }
        } )
    }

    private fun parseDateTime(date: String?, time: String?): Long? {
        return try {
            if (date.isNullOrBlank() || time.isNullOrBlank()) {
                Log.e("TicketListViewModel", "Tarih veya saat boş: date=$date, time=$time")
                return null
            }

            val format =SimpleDateFormat("EEE/dd/MMM//yyyy hh:mm a", Locale.getDefault()) // Tarih ve saat formatı
            format.timeZone = TimeZone.getDefault()
            val dateTimeString = "$date $time" // Tarih ve saat birleştirildi
            val dateTime = format.parse(dateTimeString)
            Log.d("Test", "Parsed dateTime: $dateTime")

            dateTime?.time // Milisaniyeye çevir

        } catch (e: Exception) {
            Log.e("TicketListViewModel", "Tarih/Saat dönüşüm hatası: ${e.message}")
            null
        }
    }


    /*  private fun parseDateTime(date: String?, time: String?): Long? {
            return try {
                if (date.isNullOrBlank() || time.isNullOrBlank()) {
                    Log.e("TicketListViewModel", "Tarih veya saat boş: date=$date, time=$time")
                    return null
                }

                // Tarih ve saat birleştiriliyor
                val dateTimeString = "$date $time"

                // DateTimeFormatter ile format tanımlama
                val formatter = DateTimeFormatter.ofPattern("EEE/dd/MMM hh:mm a", Locale.getDefault())

                // LocalDateTime ile string'i parse etme
                val localDateTime = LocalDateTime.parse(dateTimeString, formatter)

                // Tarihi milisaniye olarak dönüştür
                val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
                val epochMilli = instant.toEpochMilli()

                Log.d("Test", "Parsed dateTime: $localDateTime, epochMilli: $epochMilli")
                epochMilli
            } catch (e: Exception) {
                Log.e("TicketListViewModel", "Tarih/Saat dönüşüm hatası: ${e.message}")
                null
            }
        }
         */

}

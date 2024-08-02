package com.example.movieapp.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainViewModel: ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val _banner = MutableLiveData<List<SliderModel>>()
    val banners : LiveData<List<SliderModel>> = _banner

    fun loadBanner(){

        val Ref = firebaseDatabase.getReference("Banner") //FireBasedeki Banner referensına ulaşılıyor
        Ref.addValueEventListener(object :ValueEventListener{ //firebase verilerinde değişik olduğunda veri çekmek için dinleyici oluşuruldu.
            override fun onDataChange(snapshot: DataSnapshot) { //onDataChange fonksiyonu, veritabanındaki veriler her değiştiğinde tetiklenir. snapshot parametresi üzerinden veriler okunur ve SliderModel türündeki veriler lists listesine eklenir.
                val lists = mutableListOf<SliderModel>() // SliderModel nesnelerini tutmak için boş bir liste oluşturuldu
                for (child in snapshot.children){ // her bir nesne tek tek gezilir
                    val list = child.getValue(SliderModel::class.java) // her nesneyi tek tek SliderModel nesnesine dönüştürür
                    if (list != null) { // eğer liste boş değilse listeye eklenir
                        lists.add(list!!)
                    }
                }
                _banner.value = lists // _banner listesi yeni liste ile güncellenir
            }

            override fun onCancelled(error: DatabaseError) { // veri çekilirken hata oluşursa çağrılır ve hata durumu log.e kaydolur

            }
        })

    }
}
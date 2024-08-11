package com.example.movieapp.presentation.viewmodel


import android.util.Log
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

        val Ref = firebaseDatabase.getReference("Banners") //FireBasedeki Banner referensına ulaşılıyor
        Log.d("MainViewModel", "1. Firebase referansı alındı: $Ref") // 1. Adım
        Ref.addValueEventListener(object :ValueEventListener{ //firebase verilerinde değişik olduğunda veri çekmek için dinleyici oluşuruldu.
            override fun onDataChange(snapshot: DataSnapshot) { //onDataChange fonksiyonu, veritabanındaki veriler her değiştiğinde tetiklenir. snapshot parametresi üzerinden veriler okunur ve SliderModel türündeki veriler lists listesine eklenir.
                Log.d("MainViewModel", "2. Veri çekildi, snapshot alındı: ${snapshot.exists()}") // 2. Adım

                val lists = mutableListOf<SliderModel>() // SliderModel nesnelerini tutmak için boş bir liste oluşturuldu
                for (child in snapshot.children){ // her bir nesne tek tek gezilir
                    val list = child.getValue(SliderModel::class.java) // her nesneyi tek tek SliderModel nesnesine dönüştürür
                    if (list != null) { // eğer liste boş değilse listeye eklenir
                        lists.add(list!!)
                        Log.d("MainViewModel", "3. Veri listeye eklendi: $list") // 3. Adım
                    }else {
                        Log.w("MainViewModel", "3. Uyarı: List verisi null geldi") // 3. Adımda null kontrolü
                    }
                }
                _banner.value = lists // _banner listesi yeni liste ile
                Log.d("MainViewModel", "4. LiveData güncellendi: ${lists.size} öğe") // 4. Adım
            }

            override fun onCancelled(error: DatabaseError) { // veri çekilirken hata oluşursa çağrılır ve hata durumu log.e kaydolur
                Log.e("MainViewModel", "5. Firebase hatası: ${error.message}") // 5. Adım
            }
        })

    }
}
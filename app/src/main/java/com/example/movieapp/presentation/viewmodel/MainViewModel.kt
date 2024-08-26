package com.example.movieapp.presentation.viewmodel


import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.local.dao.TopMoviesDao
import com.example.movieapp.data.local.database.TopMoviesDatabase
import com.example.movieapp.domain.model.SliderModel
import com.example.movieapp.domain.model.TopMoviesModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch


class MainViewModel(application: Application): BaseViewModel(application) {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _banner = MutableLiveData<List<SliderModel>>()
    val banners : LiveData<List<SliderModel>> = _banner

    private val _topMovies = MutableLiveData<List<TopMoviesModel>>()
    val topMovies : LiveData<List<TopMoviesModel>> = _topMovies

    fun topMovies () {
        val Ref = firebaseDatabase.getReference("TopMovies")
        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<TopMoviesModel>()
                for (child in snapshot.children){
                    val list = child.getValue(TopMoviesModel::class.java)
                    if (list != null){
                        lists.add(list!!)
                    }else{
                    }
                }
                _topMovies.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun loadBanner(){

        val Ref = firebaseDatabase.getReference("Banners") //FireBasedeki Banner referensına ulaşılıyor
        Log.d("MainViewModel", "1. Firebase referansı alındı: $Ref")
        Ref.addValueEventListener(object :ValueEventListener{ //firebase verilerinde değişik olduğunda veri çekmek için dinleyici oluşuruldu.
            override fun onDataChange(snapshot: DataSnapshot) { //onDataChange fonksiyonu, veritabanındaki veriler her değiştiğinde tetiklenir. snapshot parametresi üzerinden veriler okunur ve SliderModel türündeki veriler lists listesine eklenir.
                val lists = mutableListOf<SliderModel>() // SliderModel nesnelerini tutmak için boş bir liste oluşturuldu
                for (child in snapshot.children){ // her bir nesne tek tek gezilir
                    val list = child.getValue(SliderModel::class.java) // her nesneyi tek tek SliderModel nesnesine dönüştürür
                    if (list != null) { // eğer liste boş değilse listeye eklenir
                        lists.add(list!!)
                    }else {
                    }
                }
                _banner.value = lists // _banner listesi yeni liste ile
            }

            override fun onCancelled(error: DatabaseError) { // veri çekilirken hata oluşursa çağrılır ve hata durumu log.e kaydolur
                Log.e("MainViewModel", "5. Firebase hatası: ${error.message}")
            }
        })

    }

    private fun storeInSQLite (list : List<TopMoviesModel>) {

        launch {
            val dao = TopMoviesDatabase(getApplication()).topMoviesDao()
            dao.getAllTopMovies()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }

        }
    }

}
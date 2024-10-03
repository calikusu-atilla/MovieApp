package com.example.movieapp.presentation.viewmodel


import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.data.local.database.TopMoviesDatabase
import com.example.movieapp.domain.model.SliderModel
import com.example.movieapp.domain.model.TopMoviesModel
import com.example.movieapp.domain.model.TrendingMovieModel
import com.example.movieapp.domain.model.UpcomingMoviesModel
import com.example.movieapp.domain.repository.TredingMoviesRepository
import com.example.movieapp.domain.repository.UpcomingMoviesRepository
import com.example.movieapp.util.CustomSharedPreferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application, override val upcomingMoviesRepository: UpcomingMoviesRepository, val tredingMoviesRepository: TredingMoviesRepository): BaseViewModel(application) {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 0.1 * 60 * 1000 * 1000 * 1000L

    private val _banner = MutableLiveData<List<SliderModel>>()
    val banners : LiveData<List<SliderModel>> = _banner

    private val _topMovies = MutableLiveData<List<TopMoviesModel>>()
    val topMovies : LiveData<List<TopMoviesModel>> = _topMovies

    private val _upcomingMovies = MutableLiveData<List<UpcomingMoviesModel>>()
    val upcomingMovies: LiveData<List<UpcomingMoviesModel>> = _upcomingMovies

    private val _tredingMovies = MutableLiveData<List<TrendingMovieModel>>()
    val tredingMovies: LiveData<List<TrendingMovieModel>> = _tredingMovies

    fun tredingMovies(timeWindow: String){
        viewModelScope.launch {
            try {
                val movies = tredingMoviesRepository.getTredingMovies(timeWindow = "day")
                Log.d("MainViewModel", "Apı'dan Treding referansı alındı: $movies")
                if (movies != null ) {
                    _tredingMovies.postValue(movies)
                }else{
                    Log.d("MainViewModel", "Treding Film bulunamadı")
                }

            }catch (e: Exception){
                Log.e("ViewModel", "Error loading treding movies", e)
                e.printStackTrace()  // Hatayı daha detaylı olarak loglamak
            }
        }

    }

    fun loadUpcomingMovies(page: Int) {
        viewModelScope.launch {
            try {
                val movies = upcomingMoviesRepository.getUpcomingMovies(1)
                Log.d("MainViewModel", "Apı'dan Upcoming referansı alındı: $movies")
                if (movies != null) {
                    _upcomingMovies.postValue(movies)
                } else {
                    Log.d("MainViewModel", "Upcoming Film bulunamadı")
                    // Boş veri durumu yönetimi
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error loading Upcoming movies", e)
                e.printStackTrace()  // Hatayı daha detaylı olarak loglamak
            }
        }
    }


    fun refreshData(){

        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        }else {
            topMovies()
        }

    }

    private fun getDataFromSQLite() {
        launch {
            val topmovies = TopMoviesDatabase(getApplication()).topMoviesDao().getAllTopMovies()
            _topMovies.postValue(topmovies)
            Log.d("MainViewModel", "Veriler SQLite'dan alındı.")
            Toast.makeText(getApplication(),"Sqlite",Toast.LENGTH_LONG).show()
        }
    }


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
                Toast.makeText(getApplication(),"Fireebase",Toast.LENGTH_LONG).show()
                storeInSQLite(lists) // Verileri SQLite kaydet
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainViewModel", "Firebase hatası: ${error.message}")            }
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
                Log.e("MainViewModel", "Firebase hatası: ${error.message}")
            }
        })

    }

    private fun storeInSQLite (list : List<TopMoviesModel>) {

        launch {
            val dao = TopMoviesDatabase(getApplication()).topMoviesDao()
            dao.deleteAllTopMovie()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }
        }
        customPreferences.saveTime(System.nanoTime())
    }

}
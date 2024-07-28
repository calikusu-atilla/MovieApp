package com.example.movieapp.domain.use_case.get_movies

import coil.network.HttpException
import com.example.movieapp.data.remote.dto.Search
import com.example.movieapp.data.remote.dto.toMovieList
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOError
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

// Bu sınıf, filmleri almak için kullanım durumunu tanımlar
class GetMovieUseCase @Inject constructor(private val repository: MovieRepository){

    // Filmleri almak için executeGetMovies fonksiyonu tanımlanır. Flow<Resource<List<Movie>>> tipi, Coroutine Flow kullanarak asenkron veri akışı sağlar ve Resource sınıfı ile veri durumlarını yönetir.
    fun executeGetMovies(search: String): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())     // Veri yükleniyor durumu yayımlanır
            val movieList = repository.getMovies(search)    // Repository'den film verileri alınır
            if (movieList.Response.equals("True")){             // Eğer yanıt başarılı ise
                emit(Resource.Success(movieList.toMovieList()))   // Başarı durumu yayımlanır ve film listesi döndürülür
            }else {
                emit(Resource.Error("Aradığınız film bulunamadı!")) // Hata durumu yayımlanır ve hata mesajı döndürülür
            }
        }catch (e : IOError) {      // İnternet bağlantısı hata durumu yayınlanır
            emit(Resource.Error(message = "internet bağlantınızı kontrol ediniz"))
        }catch (e : HttpException) {  // HTTP hatası durumu yayımlanır
            emit(Resource.Error(message = e.localizedMessage ?: "Error"))
        }catch (e : TimeoutException) {  // Zaman aşımı hatası durumu yayımlanır
            emit(Resource.Error(message = "İstek zaman aşımına uğradı"))
        }catch (e : UnknownHostException) { // Bilinmeyen ana bilgisayar hatası durumu yayımlanır
            emit(Resource.Error(message = "Sunucuya ulaşılamıyor, Internet bağlantınızı kontrol ediniz"))
        }catch (e : Exception) {  // Genel hata durumu yayımlanır
            emit(Resource.Error("Beklenmeyen bir hata oluştu: ${e.localizedMessage}"))
        }
    }
}

/*
Genel Anlamı

Use Case (Kullanım Durumu): GetMovieUseCase, uygulamanın iş mantığını içerir ve verilerin nasıl alınıp işleneceğini tanımlar.

Bağımlılık Enjeksiyonu: Hilt kullanılarak MovieRepository bağımlılığı otomatik olarak enjekte edilir.

Asenkron Veri Akışı: Flow kullanılarak, veriler asenkron olarak alınır ve yayımlanır.

Durum Yönetimi: Resource sınıfı kullanılarak, veri durumları (Loading, Success, Error) yönetilir.

Hata Yönetimi: Hatalar yakalanır ve uygun mesajlarla yayımlanır.
 */
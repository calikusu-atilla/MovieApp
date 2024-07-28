package com.example.movieapp.data.remote.dto

import com.example.movieapp.domain.model.Movie

// MoviesDto: API'den gelen film arama sonuçlarını temsil eden DTO (Data Transfer Object) sınıfı
data class MoviesDto(
    val Response: String, // API yanıt durumu
    val Search: List<Search>, // Arama sonuçları listesi
    val totalResults: String  // Toplam sonuç sayısı
)
// toMovieList: MoviesDto'yu domain modeline dönüştüren genişletme fonksiyonu
fun MoviesDto.toMovieList(): List<Movie> {

    // Search listesindeki her bir Search nesnesini, Movie nesnesine dönüştürerek yeni bir liste oluşturur
    return Search.map { search -> Movie(
        search.Poster,
        search.Title,
        search.Year,
        search.imdbID
    ) }
}


/*

MovieDetailDto: API'den gelen film detaylarını temsil eden bir veri transfer nesnesidir (DTO).
                Bu sınıf, API'den dönen JSON verilerini Kotlin nesnelerine dönüştürmek için kullanılır.
toMovieDetail(): Bu bir genişletme fonksiyonudur ve MovieDetailDto sınıfını MovieDetail sınıfına dönüştürür.
                 Domain modeli olan MovieDetail, uygulama içinde kullanılmak üzere daha az veri içeren ve sadece gerekli alanları barındıran bir sınıftır.
 */
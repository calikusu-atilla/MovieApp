package com.example.movieapp.data.remote.dto

import com.example.movieapp.domain.model.MovieDetail



// MovieDetailDto: API'den gelen film detaylarını temsil eden DTO (Data Transfer Object) sınıfı
data class MovieDetailDto(
    val Actors: String, // Filmde yer alan oyuncular
    val Awards: String, // Filmde kazanılan ödüller
    val BoxOffice: String, // Gişe hasılatı
    val Country: String, // Ülke bilgisi
    val DVD: String, // DVD çıkış tarihi
    val Director: String, // Yönetmen
    val Genre: String, // Film türü
    val Language: String, // Film dili
    val Metascore: String, // Metascore puanı
    val Plot: String, // Filmin konusu
    val Poster: String, // Poster URL'si
    val Production: String, // Yapımcı şirket
    val Rated: String, // Yaş sınırı
    val Ratings: List<Rating>, // Çeşitli kaynaklardan gelen puanlar listesi
    val Released: String, // Yayın tarihi
    val Response: String, // API yanıt durumu
    val Runtime: String, // Film süresi
    val Title: String, // Film başlığı
    val Type: String, // İçerik türü (film, dizi vs.)
    val Website: String, // Resmi web sitesi
    val Writer: String, // Senarist
    val Year: String, // Yayın yılı
    val imdbID: String, // IMDb ID'si
    val imdbRating: String, // IMDb puanı
    val imdbVotes: String // IMDb oy sayısı
)


// toMovieDetail: MovieDetailDto'yu domain modeline dönüştüren genişletme fonksiyonu
fun MovieDetailDto.toMovieDetail() : MovieDetail {

    // MovieDetail nesnesini döndürürken, ilgili alanları MovieDetailDto'dan alır
    return MovieDetail(Actors, Awards, Country, Director, Genre, Language, Poster, Rated, Released, Title, Type, Year, imdbRating)
}


/*

MovieDetailDto: API'den gelen film detaylarını temsil eden bir veri transfer nesnesidir (DTO).
                Bu sınıf, API'den dönen JSON verilerini Kotlin nesnelerine dönüştürmek için kullanılır.
toMovieDetail(): Bu bir genişletme fonksiyonudur ve MovieDetailDto sınıfını MovieDetail sınıfına dönüştürür.
                 Domain modeli olan MovieDetail, uygulama içinde kullanılmak üzere daha az veri içeren ve sadece gerekli alanları barındıran bir sınıftır.
 */
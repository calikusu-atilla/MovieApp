package com.example.movieapp.data.repository

import com.example.movieapp.data.remote.MovieAPI
import com.example.movieapp.data.remote.dto.MovieDetailDto
import com.example.movieapp.data.remote.dto.MoviesDto
import com.example.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

// MovieRepository arayüzünü uygulayan sınıf.
// Bu sınıf, MovieAPI kullanarak film verilerini sağlar.
class MovieRepositoryImpl @Inject constructor(private val api: MovieAPI):MovieRepository{


    // Arama terimine göre film listesini döndüren fonksiyon.
    // Bu fonksiyon, MovieAPI üzerinden API çağrısı yapar ve MoviesDto nesnesini döner.
    override suspend fun getMovies(search: String): MoviesDto {
        return api.getMovies(searchString = search)
    }


    // IMDb ID'sine göre film detaylarını döndüren fonksiyon.
    // Bu fonksiyon, MovieAPI üzerinden API çağrısı yapar ve MovieDetailDto nesnesini döner.
    override suspend fun getMovieDetail(imdbId: String): MovieDetailDto {
        return api.getMovieDetail(imdbId = imdbId )
    }
}


/*

@Inject constructor(private val api: MovieAPI): Bu sınıfın bir örneği oluşturulduğunda, MovieAPI nesnesi enjekte edilir.
Bu, Dependency Injection (bağımlılık enjeksiyonu) kullanılarak gerçekleştirilir.
Bu sayede, MovieAPI bağımlılığı sınıfa dışarıdan sağlanır ve test edilebilirlik artar.


MovieRepositoryImpl sınıfı, MovieRepository arayüzünü uygulayarak veri erişim işlemlerini gerçekleştirir.
Bu sınıf, MovieAPI kullanarak film verilerini sağlar. MovieAPI, uzak bir sunucudan (örneğin, bir REST API) veri almak için kullanılan bir arayüzdür.
Bu sayede, veri kaynağı değiştirilse bile MovieRepositoryImpl sınıfı aynı kalır, çünkü sadece MovieRepository arayüzünü uygulamaktadır.
Bu, uygulamanızın daha esnek ve bakımı kolay hale gelmesini sağlar.

 */
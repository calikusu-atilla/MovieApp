package com.example.movieapp.data.dependencyInjection

import com.example.movieapp.data.remote.MovieAPI
import com.example.movieapp.data.repository.MovieRepositoryImpl
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Bu modül, SingletonComponent içerisine enjekte edilecek şekilde yapılandırılmıştır.
object AppModule {


    // Retrofit kullanarak MovieAPI örneği sağlayan fonksiyon.
    // Bu fonksiyon, Singleton (tekil) bir bileşen sağlar.
    @Provides
    @Singleton
    fun provideMovieApi(): MovieAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)   // API'nin temel URL'sini ayarlar.
            .addConverterFactory(GsonConverterFactory.create()) // JSON verilerini dönüştürmek için Gson kullanır.
            .build()
            .create(MovieAPI::class.java) // MovieAPI arayüzünün bir uygulamasını oluşturur ve döner.
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieAPI) : MovieRepository {
        return MovieRepositoryImpl(api = api) // MovieRepositoryImpl nesnesi oluşturur ve döner.
    }

}


/*

@Provides: Bu anotasyon, bir fonksiyonun bağımlılık sağladığını belirtir.
           Bu fonksiyonlar genellikle bir nesne oluşturur ve yapılandırır.
           Dagger, ihtiyaç duyulan bağımlılıkları sağlamak için bu fonksiyonları çağırır.

@Singleton: Bu anotasyon, bir nesnenin yalnızca bir kez oluşturulmasını sağlar.
            Dagger, bu nesneyi oluşturduktan sonra her yerde aynı örneği kullanır.
            Bu, uygulama genelinde tekil bir nesneye ihtiyaç duyulduğunda faydalıdır.

Kullanım Örnekleri ve Avantajları

Bellek Yönetimi: @Singleton anotasyonu, nesnelerin yalnızca bir kez oluşturulmasını sağlar, bu da bellek kullanımını optimize eder.
Performans: Tekil nesneler tekrar tekrar oluşturulmadığı için performans iyileştirmesi sağlar.
Bağımlılık Yönetimi: @Provides ve @Singleton anotasyonları, bağımlılıkların yönetimini ve enjekte edilmesini kolaylaştırır, bu da kodun daha temiz ve sürdürülebilir olmasını sağlar.

 */
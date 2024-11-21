package com.example.movieapp.data.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.movieapp.R
import com.example.movieapp.data.local.dao.CartDao
import com.example.movieapp.data.local.database.CartDatabase
import com.example.movieapp.data.remote.MovieAPI
import com.example.movieapp.data.remote.TmdbAPI
import com.example.movieapp.data.repository.CartRepositoryImpl
import com.example.movieapp.data.repository.CastMoviesRepositoryImpl
import com.example.movieapp.data.repository.MovieRepositoryImpl
import com.example.movieapp.data.repository.TredingMoviesRepositoryImpl
import com.example.movieapp.data.repository.UpcomingMoviesRepositoryImpl
import com.example.movieapp.domain.repository.CartRepository
import com.example.movieapp.domain.repository.CastMoviesRepository
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.domain.repository.TredingMoviesRepository
import com.example.movieapp.domain.repository.UpcomingMoviesRepository
import com.example.movieapp.util.Constants.BASE_URL
import com.example.movieapp.util.Constants.BASE_URL_TMDB
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Bu modül, SingletonComponent içerisine enjekte edilecek şekilde yapılandırılmıştır.
object AppModule {

    // Uygulama genelinde tek bir FirebaseDatabase örneğini sağlar.
    @Provides
    @Singleton
    fun provideFirebaseDatabese(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }


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
    fun provideTmdbApi(): TmdbAPI { // TmdbAPI arayüzünü sağlamak için yeni bir fonksiyon
        return Retrofit.Builder()
            .baseUrl(BASE_URL_TMDB)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieAPI): MovieRepository {
        return MovieRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideTmdbRepository(api: TmdbAPI): UpcomingMoviesRepository { // Bu örnekte TMDB için başka bir repository sağlıyoruz
        return UpcomingMoviesRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideTredingRepository(api: TmdbAPI): TredingMoviesRepository {
        return TredingMoviesRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideCastRepository(api: TmdbAPI): CastMoviesRepository{
        return CastMoviesRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): CartDatabase {
        return Room.databaseBuilder(context, CartDatabase::class.java, "cartdatebase").fallbackToDestructiveMigration().build()

    }

    @Provides
    @Singleton
    fun provideCartDao(database: CartDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideCartRepository( cartDao: CartDao): CartRepository {
        return CartRepositoryImpl(cartDao)
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
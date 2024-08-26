package com.example.movieapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.data.local.converters.GenreTypeConverter
import com.example.movieapp.data.local.dao.TopMoviesDao
import com.example.movieapp.domain.model.TopMoviesModel


// Veritabanı tanımlaması yapılır. Room, @Database ile bu sınıfı veritabanı olarak tanır.
// `entities` listesinde tablo olarak kullanılacak veri sınıfı belirtilir.
@Database(entities = [TopMoviesModel::class], version = 1)
@TypeConverters(GenreTypeConverter::class)
abstract class TopMoviesDatabase : RoomDatabase() {


    // DAO sınıfını soyut bir metot olarak tanımlarız.
    // Bu metod, Room tarafından uygulandığında DAO'ya erişim sağlanır.
    abstract fun topMoviesDao(): TopMoviesDao

    companion object {

        // Volatile, bu değişkenin her zaman en güncel haliyle thread'ler arası görünür olmasını sağlar.
        @Volatile private var instance : TopMoviesDatabase? = null

        // Kilit mekanizması; birden fazla thread aynı anda bu bloğu çalıştırmaya çalıştığında birbirlerini engellemek için kullanılır.
        private val lock = Any()


        // `invoke` operatörü, bu sınıfın örneğini başlatmak için kullanılacak.
        // Eğer `instance` null ise, senkronize bir blok içinde veritabanı oluşturulacak.
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {

            // Eğer `instance` hala null ise, `makeDatabase` fonksiyonu çağrılır ve instance atanır.
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        // Bu fonksiyon veritabanını oluşturur.
        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,TopMoviesDatabase::class.java,"topmoviesdatabase"
        ).build()

    }
}



/*

Singleton Pattern ile Veritabanı Oluşturma:

@Volatile instance: instance değişkeni volatile olarak işaretlenmiş, böylece farklı thread'ler arasında görünürlük problemi yaşanmaz.
synchronized(lock): Bir kilit mekanizması kullanarak, aynı anda birden fazla thread'in veritabanı oluşturmasını engeller.
makeDatabase(context): Bu fonksiyon veritabanını oluşturur ve Room.databaseBuilder kullanarak veritabanını yapılandırır.


invoke Operator:

invoke operatörü, TopMoviesDatabase sınıfının bir örneğini almak için kullanılır. Eğer örnek (instance) mevcut değilse, makeDatabase fonksiyonu çağrılarak veritabanı oluşturulur.


 */
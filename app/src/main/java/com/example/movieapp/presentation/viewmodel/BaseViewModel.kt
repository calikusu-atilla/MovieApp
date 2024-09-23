
package com.example.movieapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.domain.repository.UpcomingMoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    // Job instance olarak oluşturulmalı
    private val job = Job()

    // CoroutineContext job ve Main dispatcher ile birleştiriliyor
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    // Soyut repository özelliği
    abstract val repository: UpcomingMoviesRepository

    // ViewModel temizlendiğinde job iptal ediliyor
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}

/*
Açıklamalar:

Job Initialization: Job nesnesini doğru bir şekilde instance olarak oluşturmanız gerekiyor.
                   private val job = Job() olarak düzeltilmiştir.

CoroutineScope: Bu yapı, asenkron görevlerinizi ViewModel yaşam döngüsü ile senkronize bir şekilde yönetmenize olanak tanır.
                job ve Dispatchers.Main ile birlikte bir CoroutineContext oluşturularak coroutine'leriniz Main thread'de çalışır.

onCleared: onCleared metodu, ViewModel yok edildiğinde çağrılır ve bu noktada job.cancel() ile devam eden tüm coroutine işlemleri iptal edilir, böylece bellek sızıntılarının önüne geçilir.

 */
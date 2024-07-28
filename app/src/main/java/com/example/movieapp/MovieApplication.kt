package com.example.movieapp

import android.app.Application
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApplication : Application() {

/*

    Hilt'i Başlatma: @HiltAndroidApp anotasyonu, Hilt'in uygulama düzeyinde bağımlılık enjeksiyonu yapılandırmasını başlatmasını sağlar.
                     Bu, Hilt'in uygulamanızın bağımlılıklarını yönetmesine ve gerekli yerlere enjekte etmesine olanak tanır.

    Application Sınıfı: MovieApplication sınıfı, Application sınıfından türetilmiştir.
                        Bu sınıf, uygulama başlatıldığında oluşturulur ve uygulama boyunca yaşar.
                        Bu sınıf, Hilt ile bağımlılık enjeksiyonu yapılandırmasının başlatıldığı yerdir.


    Bu sınıfı manifests de ekliyoruz

*/}
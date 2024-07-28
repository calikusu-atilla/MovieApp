package com.example.movieapp.util

    sealed class Resource<T>(val data: T? = null, val message:String? = null) {
        class Success<T>(data: T) : Resource<T>(data = data)
        class Error<T>(message: String, data:T? = null) : Resource<T>(data = data,message=message)
        class Loading<T>(data:T?= null) : Resource<T>(data=data)
    }

/*
Sealed Sınıfı: Sealed sınıf, belirli bir alt sınıf kümesine sahip olabilen sınıftır.
 Bu örnekte, Resource sınıfı üç olası durumu temsil eder: Success, Error ve Loading.

 Alt Sınıflar:
Success: Başarılı bir veri yükleme durumunu temsil eder.
Error: Hata durumunu temsil eder ve isteğe bağlı olarak hata mesajı ile birlikte veriyi de tutabilir.
Loading: Veri yükleme sürecinin devam ettiğini temsil eder ve isteğe bağlı olarak veriyi de tutabilir.


Bu sınıf, API isteklerinin veya uzun süren işlemlerin durumlarını yönetmek için oldukça kullanışlıdır.
Bu sayede, UI katmanında veri yükleme, başarı ve hata durumlarını kolayca yönetebilirsiniz.

 */
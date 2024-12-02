package com.example.movieapp.domain.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser


// AuthRepositoryInterFace adında bir arayüz tanımlıyoruz.
// Bu arayüz, kimlik doğrulama işlemlerinin nasıl yapılacağını belirtir.
interface AuthRepositoryInterFace {


    // Kullanıcı giriş işlemi yapar.
    // 'email' ve 'password' giriş bilgilerini alır.
    // 'callback' bir sonuç döndürür: başarı durumu (Boolean) ve hata mesajı (String?)
    fun login (email: String, password: String, callback: (Boolean,String?) -> Unit)

    // Şifre sıfırlama işlemi yapar.
    // 'email' ve 'password' sıfırlama bilgilerini alır.
    // 'callback' bir sonuç döndürür: başarı durumu (Boolean) ve hata mesajı (String?)
    fun resetPassword (email: String, callback: (Boolean, String?) -> Unit)

    // Kullanıcı kaydı oluşturur.
    // 'email' ve 'password' kayıt bilgilerini alır.
    // 'callback' bir sonuç döndürür: başarı durumu (Boolean) ve hata mesajı (String?)
    fun register(userName: String, email: String, password: String, confirmPassword: String , callback: (Boolean, String?) -> Unit)

    // Şu anki oturum açmış kullanıcıyı alır.
    // 'FirebaseUser?' tipi döner: oturum açmış kullanıcı nesnesi veya null.
    fun getCurrentUser(): FirebaseUser?

    // Kullanıcıyı çıkış yapar.
    fun logout()

    // Kullanıcının giriş yapıp yapmadığını kontrol eder.
    // Boolean döner: kullanıcı giriş yapmış mı (true) veya yapmamış mı (false).
    fun isUserLoggedIn(): Boolean

    // Google oturum açma işlemini başlatır ve sonuç döner.
    // 'GoogleSignInAccount' ve hata mesajı için bir callback alır.
    fun loginWithGoogle(account: GoogleSignInAccount, callback: (Boolean, String?) -> Unit)



}
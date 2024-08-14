package com.example.movieapp.data.source

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthManager(private val context: Context) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance() //Firebase Auth işlemleri tanımlanıyor

    fun register(email: String, password: String, callback: (Boolean, String?) -> Unit) {  //register fonsiyonu kullanıcıdan email ve password bilgileri alınır kayıt işleminin sonucunu callback atanır. callback giriş işleminin başarılı olup olmadığını belirten bir Boolean ve hata mesajını içerebilecek bir String parametre alır.
        auth.createUserWithEmailAndPassword(email, password)  //createUserWithEmailAndPassword metodu, kullanıcı oluşturma işlemini başlatır
            .addOnCompleteListener { task ->                   //addOnCompleteListener metodu ile işlem tamamlandığında yapılacakları belirtir. task nesnesi, işlemin sonucunu içerir.
                if (task.isSuccessful) {                      // Eğer giriş işlemi başarılı olursa callback fonksiyonunu çağırarak başarı durumunu true ve hata mesajı null döner.
                    val user = auth.currentUser               // giriş yapmış olan kullanıcı alınıyor
                    user?.sendEmailVerification()             // eğer mevcut kullanıcı null değilse mevcut kullanıcıya doğrulama e-postası gönderilir
                        ?.addOnCompleteListener { verifyTask ->   //sendEmailVerification() fonksiyonunun tamamlanmasını dinleyen bir OnCompleteListener ekler.
                            if (verifyTask.isSuccessful) {       // sonuç olumlu veya olumsuz  ise toast mesajı döner olumlu yada olumsuz diye
                                // Doğrulama e-postası gönderildi.
                                Toast.makeText(context,"Doğrulama e-postası gönderildi. Lütfen e-postanızı kontrol edin.", Toast.LENGTH_LONG).show()
                                val user = auth.signOut()
                            }else {
                                Toast.makeText(context,"Doğrulama e-postası gönderilemedi.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    Log.d("FirebaseAuthManager", "Register successful")
                    callback(true, null)
                } else {                                       // Eğer giriş işlemi başarısız olursa callback fonksiyonunu çağırarak başarı durumunu false ve hata mesajı döner.
                    Log.e("FirebaseAuthManager", "Register failed: ${task.exception?.message}")
                    callback(false, task.exception?.message)
                }
            }
    }



    fun login(
        email: String,
        password: String,
        callback: (Boolean, String?) -> Unit
    ) { //login fonsiyonu kullanıcıdan email ve password bilgileri alınır giriş işleminin sonucunu callback atanır. callback giriş işleminin başarılı olup olmadığını belirten bir Boolean ve hata mesajını içerebilecek bir String parametre alır.
        auth.signInWithEmailAndPassword(
            email,
            password
        )       //signInWithEmailAndPassword metodu, giriş işlemini başlatır
            .addOnCompleteListener { task -> //addOnCompleteListener metodu ile işlem tamamlandığında yapılacakları belirtir. task nesnesi, işlemin sonucunu içerir.
                val user = auth.currentUser // giriş yapmış olan kullanıcı alınıyor
                if (user?.isEmailVerified == true)   //E- posta doğrulaması kontrol ediliyor

                // E-posta doğrulanmış

                    if (task.isSuccessful) { // Eğer giriş işlemi başarılı olursa callback fonksiyonunu çağırarak başarı durumunu true ve hata mesajı null döner.
                        Log.d("FirebaseAuthManager", "Login successful")
                        callback(true, null)
                    } else {
                        //Giriş başarısız
                        Log.e("FirebaseAuthManager", "Login failed: ${task.exception?.message}")
                        callback(false, task.exception?.message)
                    } else {
                    //E-posta doğrulanmamış
                    Toast.makeText(
                        context,
                        "E-posta adresiniz doğrulanmamış.Lütfen e- postanızı kontrol edin. ",
                        Toast.LENGTH_LONG
                    ).show()
                    auth.signOut()
                    callback(false, "E-posta doğrulanmamış")
                }
            }
    }

    fun logout(){
        auth.signOut() //logout fonksiyonu mevcut kullanıcıdan çıkış yapar
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser //oturum açmış olan kullanıcıyı döner. Eğer oturum açmış bir kullanıcı yoksa null döner
    }

    fun resetPassword(email: String, callback: (Boolean, String?) -> Unit) { //resetPassword fonsiyonu kullanıcıdan email bilgisini alınır alınan e-postanın sonucu callback atanır. callback girilen e-posta bilgisi başarılı olup olmadığını belirten bir Boolean ve hata oluşmuşsa hata mesajını içerebilecek bir String parametre alır.
        auth.sendPasswordResetEmail(email) // sendPasswordResetEmail metodunu belirtilen e-posta adresine şifre sıfırlama e-postası gönderir.
            .addOnCompleteListener { task ->   //addOnCompleteListener metodu ile işlem tamamlandığında yapılacakları belirtir. task nesnesi, işlemin sonucunu içerir.
                if (task.isSuccessful) {      // Eğer giriş işlemi başarılı olursa callback fonksiyonunu çağırarak başarı durumunu true ve hata mesajı null döner.
                    Log.d("FirebaseAuthManager", "Password reset email sent")
                    callback(true, null)     //callback fonksiyonunu çağırır. İlk parametre true, işlemin başarılı olduğunu gösterir. İkinci parametre null, hata olmadığını gösterir.
                } else {
                    Log.e("FirebaseAuthManager", "Password reset failed: ${task.exception?.message}")
                    callback(false, task.exception?.message)  //callback fonksiyonunu çağırır. İlk parametre false, işlemin başarısız olduğunu gösterir. İkinci parametre, hata mesajını içerir
                }
            }

    }













}

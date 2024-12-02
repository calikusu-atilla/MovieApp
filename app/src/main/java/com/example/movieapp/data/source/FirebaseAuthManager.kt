package com.example.movieapp.data.source

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.movieapp.domain.model.UserModel
import com.example.movieapp.presentation.ui.LoginActivity
import com.example.movieapp.presentation.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class FirebaseAuthManager(private val context: Context) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance() //Firebase Auth işlemleri tanımlanıyor
    private val firebaseDatabase : DatabaseReference = Firebase.database.reference


    fun loginWithGoogle(googleSignInAccount: GoogleSignInAccount, callback: (Boolean, String?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                user?.let {
                    saveGoogleUserData(it.displayName ?: "Unknown", it.email ?: "", "")
                    updateUi(it)
                    callback(true, null)
                } ?: callback(false, "Firebase user is null")
            } else {
                Toast.makeText(context, "Account Creation Fail", Toast.LENGTH_SHORT).show()
                Log.d("Account", "Google Sign-In Failure", task.exception)
                callback(false, task.exception?.message)
            }
        }
    }


    private fun saveGoogleUserData(userName: String, email: String, password: String) {
        val user = UserModel(userName, email, password)
        val userId = auth.currentUser?.uid ?: return
        firebaseDatabase.child("User").child(userId).setValue(user)
    }


    fun register(userName: String, email: String, password: String, confirmPassword: String, callback: (Boolean, String?) -> Unit) {

        if (userName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(context, "Please fill all details",Toast.LENGTH_SHORT).show()
        }else{
            createAccount(userName, email, password, confirmPassword, callback)
        }
    }

    private fun createAccount(userName: String, email: String, password: String, confirmPassword: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                saveUserData(userName,email,password,confirmPassword)
                Toast.makeText(context,"Account created successfully", Toast.LENGTH_SHORT).show()
                sendEmailVerification(callback)
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
                if (context is Activity) {
                    context.finish()
                }
            }else {
                Toast.makeText(context,"Account Creation Fail", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
                callback(false, task.exception?.message)
            }
        }
    }


    //save data in to database
    private fun saveUserData(userName: String, email: String, password: String, confirmPassword: String) {

        val user = UserModel(userName,email,password,confirmPassword)
        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid

        //save user data Firebase database
        userId?.let { userId ->
            firebaseDatabase.child("User").child(userId).setValue(user)
        }
    }

    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {

        if (email.isBlank() || password.isBlank()){
            Toast.makeText(context,"Please fill all details", Toast.LENGTH_SHORT).show()
        }else {
            createUserAccount(email,password,callback)
        }
    }

    private fun createUserAccount(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user = auth.currentUser
                if (user?.isEmailVerified == true) {
                    updateUi(user)
                    callback(true,null)
                }else {
                    Toast.makeText(context, "E-posta adresiniz doğrulanmamış. Lütfen e-postanızı kontrol edin.", Toast.LENGTH_LONG).show()
                    auth.signOut() // Doğrulama yapılmamışsa oturumu kapat
                    callback(false, "E-posta doğrulanmamış")
                }
            }else {
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        val newUser = auth.currentUser
                        loginsaveUserData(email,password)
                        Toast.makeText(context,"Account created successfully", Toast.LENGTH_SHORT).show()
                        sendEmailVerification(callback)
                        updateUi(newUser)
                        callback(true, null)
                    }else {
                        Toast.makeText(context,"Authentication faile", Toast.LENGTH_SHORT).show()
                        Log.d("Account","createUserAccount: Authentication failed", task.exception)
                        callback(false,task.exception?.message)
                    }
                }
            }
        }
    }

    private fun loginsaveUserData(email: String, password: String) {
        val user = UserModel("",email, password, "")
        val userId: String? = FirebaseAuth.getInstance().currentUser?.uid

        userId?.let { userId ->
            firebaseDatabase.child("User").child(userId).setValue(user)
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        context.startActivity(Intent(context, MainActivity::class.java))
        if (context is Activity) {
            context.finish() }
    }

    fun logout(){
        auth.signOut() //logout fonksiyonu mevcut kullanıcıdan çıkış yapar
        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
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

    fun sendEmailVerification(callback: (Boolean, String?) -> Unit) {
        val user = auth.currentUser
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener { verifyTask ->
                if (verifyTask.isSuccessful) {
                    Log.d("FirebaseAuthManager", "Verification email sent successfully.")
                    callback(true, null)
                    auth.signOut()
                } else {
                    Log.e("FirebaseAuthManager", "Failed to send verification email: ${verifyTask.exception?.message}")
                    callback(false, verifyTask.exception?.message)
                }
            }
        } else {
            Log.e("FirebaseAuthManager", "No user is logged in.")
            callback(false, "No user is logged in.")
        }
    }
}

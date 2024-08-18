package com.example.movieapp.presentation.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Status bar'ı şeffaf yapma ve içerik ile bütünleştirme
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = true // Durum çubuğundaki simgeleri açık renkte yapar

        // Status bar'ı tamamen şeffaf yap
        window.statusBarColor = android.graphics.Color.TRANSPARENT

        window.setFlags( //setFlags pencerenin davranışını ve görünümünü kontrol etmek için belirtilen pencere bayraklarını ayarlar.
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            //pencere düzeninin ekran sınırlarının ötesine geçmesine izin veren FLAG_LAYOUT_NO_LIMITS bayrağını ayarlamaktadır. Bu sayede, pencere düzeni ekranın üst, alt, sağ veya sol kenarının ötesine taşabilir.

        )
    }
}
        /*

       Açıklama:

       WindowCompat.setDecorFitsSystemWindows(window, false): Bu satır, sistem çubuklarının (status bar ve navigation bar) üstündeki alanı doldurmasını önler, bu sayede içerik bu çubukların altına taşabilir.
       WindowInsetsControllerCompat: Status bar ikonlarının rengini ve görünümünü kontrol eder. Örnekte, status bar simgeleri açık renkte ayarlanmıştır (isAppearanceLightStatusBars = true).
       window.statusBarColor = android.graphics.Color.TRANSPARENT: Status bar'ı şeffaf yapar.

         */
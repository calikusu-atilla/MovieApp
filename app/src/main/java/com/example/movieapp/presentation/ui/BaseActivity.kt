package com.example.movieapp.presentation.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags( //setFlags pencerenin davranışını ve görünümünü kontrol etmek için belirtilen pencere bayraklarını ayarlar.
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            //pencere düzeninin ekran sınırlarının ötesine geçmesine izin veren FLAG_LAYOUT_NO_LIMITS bayrağını ayarlamaktadır. Bu sayede, pencere düzeni ekranın üst, alt, sağ veya sol kenarının ötesine taşabilir.

        )

    }
}
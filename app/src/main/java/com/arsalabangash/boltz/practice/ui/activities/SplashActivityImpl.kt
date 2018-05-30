package com.arsalabangash.boltz.practice.ui.activities

import android.content.Intent
import android.os.Bundle

class SplashActivityImpl : SplashActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startIntent = Intent(this, MainActivity::class.java)
        startActivity(startIntent)
        finish()
    }

}
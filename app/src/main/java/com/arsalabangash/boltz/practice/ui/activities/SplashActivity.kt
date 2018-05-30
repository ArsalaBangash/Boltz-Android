package com.arsalabangash.boltz.practice.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arsalabangash.boltz.practice.R

abstract class SplashActivity : AppCompatActivity() {
    protected lateinit var startIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}

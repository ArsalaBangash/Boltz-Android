package com.arsalabangash.boltz.practice.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arsalabangash.boltz.practice.BoltzPracticeApp
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.challenge.ChallengeUtils
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var challengeUtils: ChallengeUtils
    private lateinit var startIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        BoltzPracticeApp.component.inject(this)
        challengeUtils.initialize()
        startIntent = Intent(this, MainActivity::class.java)
        startActivity(startIntent)
        finish()
    }
}

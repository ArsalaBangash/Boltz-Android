package com.arsalabangash.boltz.practice

import android.app.Application
import com.arsalabangash.boltz.practice.challenge.ChallengeUtils


class BoltzPracticeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ChallengeUtils.getInstance(this)
    }

}
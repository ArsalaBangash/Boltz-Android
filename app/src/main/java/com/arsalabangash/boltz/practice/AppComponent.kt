package com.arsalabangash.boltz.practice

import android.app.Application
import com.arsalabangash.boltz.practice.challenge.ChallengeGenerator
import com.arsalabangash.boltz.practice.challenge.ChallengeUtils
import com.arsalabangash.boltz.practice.ui.activities.SplashActivity
import com.arsalabangash.boltz.practice.ui.controllers.PracticeController
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(application: Application)
    fun inject(challengeGenerator: ChallengeGenerator)
    fun inject(challengeUtils: ChallengeUtils)
    fun inject(presenter: PracticeController)
    fun inject(splashActivity: SplashActivity)
}
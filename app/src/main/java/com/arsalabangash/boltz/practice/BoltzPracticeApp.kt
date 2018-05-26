package com.arsalabangash.boltz.practice

import android.app.Application


class BoltzPracticeApp : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        component.inject(this)
    }

}
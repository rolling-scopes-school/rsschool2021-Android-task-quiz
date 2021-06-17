package com.rsschool.quiz

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.rsschool.quiz.di.AppContainer

class QuizApplication : Application() {

    val appContainer by lazy { AppContainer(applicationContext) }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}

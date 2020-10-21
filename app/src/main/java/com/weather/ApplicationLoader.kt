package com.weather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationLoader : Application() {

    override fun onCreate() {
        super.onCreate()


    }
}
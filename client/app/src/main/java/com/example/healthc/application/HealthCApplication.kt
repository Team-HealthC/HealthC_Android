package com.example.healthc.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HealthCApplication : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}
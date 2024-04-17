package com.android.imageloadingapplication

import android.app.Application
import com.android.imageloadingapplication.di.ApplicationComponents
import com.android.imageloadingapplication.di.DaggerApplicationComponents

class ImageApplication : Application() {

    lateinit var applicationComponents: ApplicationComponents
    override fun onCreate() {
        super.onCreate()
        applicationComponents = DaggerApplicationComponents.builder().build()
    }
}
package com.android.imageloadingapplication.di

import com.android.imageloadingapplication.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponents {

    fun inject(mainActivity: MainActivity)
}
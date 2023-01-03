package com.karsoft.newcastleproject

import android.app.Application
import com.karsoft.newcastleproject.di.adapterModule
import com.karsoft.newcastleproject.di.networkModule
import com.karsoft.newcastleproject.di.viewModelModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


@HiltAndroidApp
class MyApp: Application(){
    override fun onCreate() {
        super.onCreate()

        val modules = listOf(networkModule, viewModelModule, adapterModule)

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            androidFileProperties()
            koin.loadModules(modules)
        }
    }
}
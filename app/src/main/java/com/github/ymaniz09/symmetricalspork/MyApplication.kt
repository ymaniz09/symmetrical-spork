package com.github.ymaniz09.symmetricalspork

import android.app.Application
import com.github.ymaniz09.symmetricalspork.di.appModule
import com.github.ymaniz09.symmetricalspork.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(appModule, retrofitModule))
        }
    }
}
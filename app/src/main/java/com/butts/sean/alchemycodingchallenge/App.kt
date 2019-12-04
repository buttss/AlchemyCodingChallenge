package com.butts.sean.alchemycodingchallenge

import android.app.Application
import com.butts.sean.alchemycodingchallenge.di.injectFeature
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@App)
        }

        injectFeature()
    }
}
package com.jhdroid.ble.sample

import android.app.Application
import timber.log.Timber

class BleApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
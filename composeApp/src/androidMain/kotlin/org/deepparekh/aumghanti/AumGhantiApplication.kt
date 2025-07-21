package org.deepparekh.aumghanti

import android.app.Application
import org.deepparekh.aumghanti.di.initKoin
import org.koin.android.ext.koin.androidContext

class AumGhantiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AumGhantiApplication)
        }
    }
}
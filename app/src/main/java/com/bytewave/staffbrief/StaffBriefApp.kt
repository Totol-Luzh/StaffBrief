package com.bytewave.staffbrief

import android.app.Application
import com.bytewave.staffbrief.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class StaffBriefApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@StaffBriefApp)
            modules(domainModule)
        }
    }
}
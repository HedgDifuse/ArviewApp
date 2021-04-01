package com.hedgdifuse.arviewapp

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.hedgdifuse.arviewapp.di.androidModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * [ArviewApp] - implementation of [Application]
 */
class ArviewApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // Init fresco
        Fresco.initialize(this)

        // Start Koin injection
        startKoin {
            androidContext(this@ArviewApp)
            modules(androidModules)
        }
    }
}
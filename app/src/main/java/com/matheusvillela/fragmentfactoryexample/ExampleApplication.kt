package com.matheusvillela.fragmentfactoryexample

import android.app.Application
import toothpick.ktp.KTP

class ExampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KTP.openRootScope()
            .installModules(AppModule(this))
    }
}
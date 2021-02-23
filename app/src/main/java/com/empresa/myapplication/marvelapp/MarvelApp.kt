package com.empresa.myapplication.marvelapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class MarvelApp : Application() {

    //private var sAnalytics: GoogleAnalytics? = null

    companion object {
        var instance: MarvelApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun getPreferencesInstance(): SharedPreferences {
            val sharedPreferencesFile = "MarvelAppSharedPreferences"
            return applicationContext().getSharedPreferences(
                sharedPreferencesFile,
                Context.MODE_PRIVATE
            )
        }
    }

    init {
        instance = this
    }

    /*override fun onCreate() {
        super.onCreate()
        sAnalytics = GoogleAnalytics.getInstance(this)

        *//*if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }*//*

        startKoin {
            androidContext(this@FlowApp)
            modules(appModules)
        }
    }*/
}
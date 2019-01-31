package com.example.gitusersearch

import android.app.Application
import com.example.gitusersearch.di.ApiRepositoryModule
import com.example.gitusersearch.di.AppComponent
import com.example.gitusersearch.di.DaggerAppComponent

class SearchAppClass : Application() {

    companion object {
        var APP_INSTANCE : SearchAppClass ?= null
        fun getAppInstance() : SearchAppClass? {
            return APP_INSTANCE
        }
    }
    var appComponent : AppComponent ?= null
    override fun onCreate() {
        super.onCreate()

        APP_INSTANCE = this
        appComponent = DaggerAppComponent.builder().apiRepositoryModule(ApiRepositoryModule()).build()
    }

    fun getappComponent() : AppComponent? {
        return appComponent
    }
}
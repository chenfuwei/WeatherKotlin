package com.custom.weather.ui

import android.app.Application
import com.custom.weather.extesions.DelegatesExt

class App : Application(){
    companion object {
        var instance : Application by DelegatesExt.notNullSingleValue()
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
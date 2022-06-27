package com.midterm.english_app.view

import android.app.Application
import com.midterm.english_app.business.component.AppComponent
import com.midterm.english_app.business.component.DaggerAppComponent

import com.midterm.english_app.business.module.*
import java.lang.Exception

open class App : Application() {

    companion object{
        lateinit var appComponent: AppComponent
       lateinit var appModule: DaggerAppComponent.Builder
        fun isAppComponentInitialized() = ::appComponent.isInitialized

    }

    private fun setAppModuleDefault() {
        DaggerAppComponent.builder()
            .applicationContextModule(ApplicationContextModule(applicationContext))

            .retrofitModule(RetrofitModule())
            .translateModule(TranslateModule())
            .databaseModule(DatabaseModule())
            .viewModelModule(ViewModelModule())
            .fragmentModule(FragmentModule())
            .adapterModule(AdapterModule())

    }

    override fun onCreate() {
        super.onCreate()
        try {
         //   setAppModuleDefault()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
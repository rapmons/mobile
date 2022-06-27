package com.midterm.english_app.business.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationContextModule(private val context: Context) {

    @Provides
    fun getContext(): Context {
        return context
    }
}
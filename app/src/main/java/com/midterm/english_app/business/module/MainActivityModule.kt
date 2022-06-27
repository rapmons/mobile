package com.midterm.english_app.business.module

import com.midterm.english_app.view.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private val mainActivity: MainActivity) {

    @Provides
    fun getMainActivityContextModule(): MainActivity {
        return mainActivity
    }
}
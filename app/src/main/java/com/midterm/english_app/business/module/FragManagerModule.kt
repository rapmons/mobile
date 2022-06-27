package com.midterm.english_app.business.module

import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides

@Module
class FragManagerModule(private val supportFragmentManager: FragmentManager?) {
    @Provides
    fun getSupportFragmentManager(): FragmentManager? {
        return supportFragmentManager
    }
}
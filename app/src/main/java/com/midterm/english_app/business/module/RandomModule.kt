package com.midterm.english_app.business.module

import com.midterm.english_app.business.provider.RandomProvider
import dagger.Module
import dagger.Provides

@Module
class RandomModule {

    @Provides
    fun getRandom(): RandomProvider {
        return RandomProvider()
    }
}
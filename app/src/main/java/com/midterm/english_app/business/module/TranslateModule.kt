package com.midterm.english_app.business.module

import com.midterm.english_app.business.provider.FirebaseTranslateProvider
import com.midterm.english_app.business.provider.TranslateModelProvider
import com.midterm.english_app.business.provider.TranslateProvider
import dagger.Module
import dagger.Provides

@Module
class TranslateModule {

    @Provides
    fun getTranslateProvider(): TranslateProvider {
        return TranslateProvider()
    }

    @Provides
    fun getFirebaseTranslatorProvider(): FirebaseTranslateProvider{
        return FirebaseTranslateProvider()
    }

    @Provides
    fun getTranslateModelProvider(): TranslateModelProvider{
        return TranslateModelProvider()
    }
}
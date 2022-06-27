package com.midterm.english_app.business.module

import com.midterm.english_app.business.provider.FirebaseDatabaseManager
import com.midterm.english_app.business.provider.FirebaseDatabaseProvider
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @Provides
    fun getFirebaseDatabaseManager(): FirebaseDatabaseManager {
        return FirebaseDatabaseManager()
    }

    @Provides
    fun getFirebaseDatabaseProvider(): FirebaseDatabaseProvider {
        return FirebaseDatabaseProvider()
    }
}
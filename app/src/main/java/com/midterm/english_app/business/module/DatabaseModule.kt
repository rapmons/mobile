package com.midterm.english_app.business.module

import android.content.Context
import com.midterm.english_app.business.provider.DatabaseManager
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule{

    @Provides
    fun getDatabaseManager(context: Context): DatabaseManager{
        return DatabaseManager(context)
    }
}
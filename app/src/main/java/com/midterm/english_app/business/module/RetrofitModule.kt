package com.midterm.english_app.business.module

import com.midterm.english_app.business.provider.RetrofitProvider
import dagger.Module
import dagger.Provides

@Module
class RetrofitModule {

    @Provides
    fun getRetrofitProvider() : RetrofitProvider{
        return RetrofitProvider
    }
}
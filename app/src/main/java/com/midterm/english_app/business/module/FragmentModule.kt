package com.midterm.english_app.business.module

import com.midterm.english_app.view.FavouriteFragment
import com.midterm.english_app.view.TensesFragment
import com.midterm.english_app.view.TranslateFragment
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {
    @Provides
    fun getFavouriteFragment(): FavouriteFragment{
        return FavouriteFragment()
    }

    @Provides
    fun getTensesFragment(): TensesFragment{
        return TensesFragment()
    }

    @Provides
    fun getTranslateFragment(): TranslateFragment {
        return TranslateFragment()
    }
}
package com.midterm.english_app.business.module

import com.midterm.english_app.viewmodel.FavouriteViewModel
import com.midterm.english_app.viewmodel.TensesRouterViewModel
import com.midterm.english_app.viewmodel.TensesViewModel
import com.midterm.english_app.viewmodel.TranslateViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {
    @Provides
    fun getTranslateViewModel(): TranslateViewModel {
        return TranslateViewModel()
    }

    @Provides
    fun getTensesViewModel(): TensesViewModel{
        return TensesViewModel()
    }

    @Provides
    fun getTensesRouterViewModel(): TensesRouterViewModel {
        return TensesRouterViewModel()
    }

    @Provides
    fun getFavouriteViewModel(): FavouriteViewModel {
        return FavouriteViewModel()
    }
}
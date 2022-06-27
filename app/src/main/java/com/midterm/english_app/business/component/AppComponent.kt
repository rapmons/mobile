package com.midterm.english_app.business.component

import com.midterm.english_app.business.module.*
import com.midterm.english_app.business.provider.*
import com.midterm.english_app.view.*
import com.midterm.english_app.viewmodel.FavouriteViewModel

import com.midterm.english_app.viewmodel.TranslateViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationContextModule::class, RetrofitModule::class,
    TranslateModule::class, DatabaseModule::class, ViewModelModule::class, FragmentModule::class,
AdapterModule::class, FirebaseModule::class, RandomModule::class, MainActivityModule::class])
interface AppComponent {
    @Singleton
    fun inject(fragment: TranslateFragment)
    @Singleton
    fun inject(fragment: FavouriteFragment)
    @Singleton
    fun inject(fragment: TensesFragment)
    @Singleton
    fun inject(activity: TensesActivity)
    @Singleton
    fun inject(activity: MainActivity)


    @Singleton
    fun inject(translateViewModel: TranslateViewModel)
    @Singleton
    fun inject(favouriteViewModel: FavouriteViewModel)
//    @Singleton
//    fun inject(settingsViewModel: SettingsViewModel)

    @Singleton
    fun inject(translateProvider: TranslateProvider)
    @Singleton
    fun inject(translateModelProvider: TranslateModelProvider)
//    @Singleton
//    fun inject(notificationManager: NotificationManager)
    @Singleton
    fun inject(notificationChannelProvider: NotificationChannelProvider)
    @Singleton
    fun inject(firebaseDatabaseManager: FirebaseDatabaseManager)
    @Singleton
    fun inject(databaseManager: DatabaseManager)
//    @Singleton
//    fun inject(serviceState: ServiceState)
}

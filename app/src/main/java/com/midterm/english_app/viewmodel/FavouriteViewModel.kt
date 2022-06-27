package com.midterm.english_app.viewmodel

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter

import androidx.recyclerview.widget.RecyclerView
import com.midterm.english_app.business.provider.DatabaseManager
import com.midterm.english_app.business.provider.FirebaseDatabaseManager
import com.midterm.english_app.model.FavouriteModel
import com.midterm.english_app.view.App

import javax.inject.Inject

class FavouriteViewModel : BaseObservable {

    @Inject
    constructor() {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var databaseManager: DatabaseManager
    @Inject
    lateinit var firebaseDatabaseManager: FirebaseDatabaseManager

    private val favouriteData = ArrayList<FavouriteModel.Item>()
    @Bindable
    val goSettings = true

    @Bindable
    fun getFavouriteData(): ArrayList<FavouriteModel.Item> {
        return favouriteData
    }

    fun updateFavouriteData(favouriteData: ArrayList<FavouriteModel.Item>) {
        this.favouriteData.addAll(favouriteData)
        notifyChange()
    }

    fun setFavouriteData(favouriteData: ArrayList<FavouriteModel.Item>) {
        clearFavoriteData()
        this.favouriteData.addAll(favouriteData)
        notifyChange()
    }

    fun clearFavoriteData() {
        favouriteData.clear()
        notifyChange()
    }

    fun removeFavoriteData(index: Int) {
        databaseManager.deleteVocabularyByName(this.favouriteData[index].vocabulary)
        firebaseDatabaseManager.deleteFavouriteByVocabulary(this.favouriteData[index].vocabulary)
        this.favouriteData.removeAt(index)

        //notifyPropertyChanged(BR.favouriteData)
    }

    companion object {
        @BindingAdapter("data")
        @JvmStatic
        fun setData(recyclerView: RecyclerView, data: ArrayList<FavouriteModel.Item>) {
            if (recyclerView.adapter is com.midterm.english_app.view.adapter.FavouriteAdapter) {
                Log.d("recyclerView.adapter", "Not null")
                (recyclerView.adapter as com.midterm.english_app.view.adapter.FavouriteAdapter).apply {
                    clearData()
                    for (vocabulary in data)
                        updateData(null, vocabulary)
                    //setData(data)
                }
            } else {
                Log.d("recyclerView.adapter", "Null")
            }
        }

//        @BindingAdapter("goSettings")
//        @JvmStatic
//        fun goSettings(view: View, boolean: Boolean) {
//            if (boolean) {
//                view.setOnClickListener {
//                    val intent = Intent(view.context, SettingsActivity::class.java)
//                    view.context.startActivity(intent)
//                }
//            }
//        }
    }
}
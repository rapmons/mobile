package com.midterm.english_app.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView


import com.midterm.english_app.R
import com.midterm.english_app.business.provider.DatabaseManager
import com.midterm.english_app.business.provider.FirebaseDatabaseManager

import com.midterm.english_app.business.provider.TranslateProvider
import com.midterm.english_app.model.FavouriteModel
import com.midterm.english_app.model.TranslateModel
import com.midterm.english_app.view.App
import javax.inject.Inject


class TranslateViewModel : BaseObservable {

    @Inject
    constructor() {
        App.appComponent.inject(this)
    }

    private val translateView = TranslateModel.TranslateView("", "")
    private val buttonText = TranslateModel.ButtonText("Translate now")


    @Inject
    lateinit var translateProvider: TranslateProvider
    @Inject
    lateinit var databaseManager: DatabaseManager
    @Inject
    lateinit var firebaseDatabaseManager: FirebaseDatabaseManager

    @Bindable
    val useAnimate = true
    @Bindable
    val positionCursorAlwaysEnd = true
    @Bindable
    val useSuggest = true

    private var buttonClearInputState = false
    private var translated = false
    private var favoriteState = false
    private var favoriteDrawable = R.drawable.ic_star_border

    private fun isValid(): Boolean {
        return translateView.inputText.isNotEmpty() && translateView.inputText.isNotEmpty()
    }

    @Bindable
    fun getInputText(): String {
        return translateView.inputText
    }

    @Bindable
    fun getResultText(): String {
        return translateView.resultText
    }

    @Bindable
    fun getButtonText(): String {
        return buttonText.text
    }

    @Bindable
    fun getFavoriteDrawable(): Int {
        return favoriteDrawable
    }

    @Bindable
    fun getTranslated(): Boolean {
        return translated
    }

    @Bindable
    fun getButtonClearInputState(): Boolean {
        return buttonClearInputState
    }


    fun setInputText(inputText: String) {
        translateProvider.callCancel()
        val inputClear = inputText.replace(Regex("[^a-zA-Z ]"), "")
        if (inputClear != getInputText()) {
            setResultText("")
            setTranslated(false)
            favoriteDrawable = R.drawable.ic_star_border
            setFavoriteDrawable(favoriteDrawable)
        }
        translateView.inputText = inputClear
        setButtonText("Translate now")

        setButtonClearInputState(translateView.inputText.isNotEmpty())

        notifyChange()
    }

    fun setResultText(resultText: String) {
        translateView.resultText = resultText.replace("\n", "")
        notifyChange()
    }

    fun setButtonText(buttonText: String) {
        this.buttonText.text = buttonText
        notifyChange()
    }

    fun setFavoriteDrawable(drawable: Int) {
        this.favoriteDrawable = drawable
        notifyChange()
    }

    fun setTranslated(boolean: Boolean) {
        this.translated = boolean
        favoriteState = false

        if (boolean && databaseManager.isVocabularyExists(getInputText())) {
            favoriteState = true
            favoriteDrawable = R.drawable.ic_star_fit
            setFavoriteDrawable(favoriteDrawable)
        }
        notifyChange()
    }

    fun setButtonClearInputState(boolean: Boolean) {
        buttonClearInputState = boolean
        notifyChange()
    }

    fun translateOnCLick() {
        if (isValid()) {
            setTranslated(false)
            Log.d("InputText", "Is valid")
            setButtonText("TRANSLATING...")
            translateProvider.Builder().offlineTranslate(this, getInputText())
            //translateProvider.Builder().onlineTranslate(this, getInputText(), UrlProvider.Yandex.format, UrlProvider.Yandex.lang, UrlProvider.Yandex.key)
        } else {
            Log.d("InputText", "Is not valid")
            setResultText("")
        }
    }

    fun favoriteOnClick() {
        favoriteState = !favoriteState
        val accID = databaseManager.getAccID()
        Log.d("favoriteState", favoriteState.toString())

        if (favoriteState) {
            // click to save
            favoriteDrawable = R.drawable.ic_star_fit
            if (getResultText().isNotEmpty() && getResultText().isNotBlank()) {
                val favouriteModel = FavouriteModel.Item(null, null, getInputText(), getResultText(), "")
                databaseManager.insertVocabulary(favouriteModel)
                if (accID != null && accID.isNotEmpty() && accID.isNotBlank())
                    firebaseDatabaseManager.insertFavourite(favouriteModel)
            }
        } else {
            // click to delete
            favoriteDrawable = R.drawable.ic_star_border
            databaseManager.deleteVocabularyByName(getInputText())
            if (accID != null && accID.isNotEmpty() && accID.isNotBlank())
                firebaseDatabaseManager.deleteFavouriteByVocabulary(getInputText())

        }

        setFavoriteDrawable(favoriteDrawable)
    }

    fun clearInputClick() {
        setInputText("")
    }

    companion object {
        @BindingAdapter("useEffect")
        @JvmStatic

        fun effect(button: View, useEffect: Boolean) {
            if (useEffect) {
                button.setOnTouchListener { _, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            button.setBackgroundColor(button.resources.getColor(R.color.colorYellowDark2))
                        }
                        MotionEvent.ACTION_UP -> {
                            button.setBackgroundColor(button.resources.getColor(R.color.colorYellow))
                        }
                        else -> {
                        }
                    }
                    false
                }
            }
        }

        @BindingAdapter("positionCursorAlwaysEnd")
        @JvmStatic
        fun setPosition(editText: EditText, inputText: String) {
            if (inputText.isNotEmpty())
                editText.setSelection(inputText.length)
        }

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageDrawable(imageView: ImageView, drawable: Int) {
            imageView.setImageDrawable(imageView.resources.getDrawable(drawable))
        }
    }

}
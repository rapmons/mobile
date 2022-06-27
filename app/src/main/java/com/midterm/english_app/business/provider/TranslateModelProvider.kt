package com.midterm.english_app.business.provider

import android.util.Log
import com.midterm.english_app.view.App
import com.midterm.english_app.view.update.OnDownloadModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslateModelProvider {

    @Inject
    constructor() {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var firebaseTranslateProvider: FirebaseTranslateProvider

    fun download(onDownloadModel: OnDownloadModel) {
        onDownloadModel.onDownload()
        firebaseTranslateProvider.translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                Log.d("Translate Model", "Downloaded")
                onDownloadModel.onSuccess()
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                onDownloadModel.onFailure()
            }

    }
}
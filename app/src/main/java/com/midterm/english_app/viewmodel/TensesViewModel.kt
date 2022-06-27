package com.midterm.english_app.viewmodel

import android.app.Activity
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import android.view.ContextThemeWrapper
import android.view.View


class TensesViewModel : BaseObservable() {

    private var title = ""
    private var description = ""
    private var look = ""
    private var manual = ""
    private var affirmation = ""
    private var example1 = ""
    private var negative = ""
    private var example2 = ""
    private var question = ""
    private var example3 = ""

    @Bindable
    val useClickBack = true

    @Bindable
    fun getTitle(): String {
        return title
    }

    @Bindable
    fun getDescription(): String {
        return description
    }

    @Bindable
    fun getLook(): String {
        return look
    }

    @Bindable
    fun getManual(): String {
        return manual
    }

    @Bindable
    fun getAffirmation(): String {
        return affirmation
    }

    @Bindable
    fun getQuestion(): String {
        return question
    }

    @Bindable
    fun getNegative(): String {
        return negative
    }

    @Bindable
    fun getExample1(): String {
        return example1
    }

    @Bindable
    fun getExample2(): String {
        return example2
    }

    @Bindable
    fun getExample3(): String {
        return example3
    }

    fun setTitle(title: String) {
        this.title = title
        notifyChange()
    }

    fun setDescription(description: String) {
        this.description = description
        notifyChange()
    }

    fun setLook(look: String) {
        this.look = look
        notifyChange()
    }

    fun setManual(manual: String) {
        this.manual = manual
        notifyChange()
    }

    fun setAffirmation(affirmation: String) {
        this.affirmation = affirmation
        notifyChange()
    }

    fun setNegative(negative: String) {
        this.negative = negative
        notifyChange()
    }

    fun setQuestion(question: String) {
        this.question = question
        notifyChange()
    }

    fun setExample1(example1: String) {
        this.example1 = example1
        notifyChange()
    }

    fun setExample2(example2: String) {
        this.example2 = example2
        notifyChange()
    }

    fun setExample3(example3: String) {
        this.example3 = example3
        notifyChange()
    }

    companion object {
        @BindingAdapter("clickBack")
        @JvmStatic
        fun clickBack(view: View, value: Boolean) {
            if (value) {
                view.setOnClickListener {
                    val context = (view.context as ContextThemeWrapper).baseContext
                    if (context is Activity)
                        (context as Activity).finish()
                }
            }
        }
    }
}
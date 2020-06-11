package com.kadencelibrary.base

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import android.content.Context
import io.reactivex.disposables.CompositeDisposable

open class KadenceVm constructor() : ViewModel() {

    val disposables = CompositeDisposable()


    override fun onCleared() {
        disposables.dispose()
        disposables.clear()
        super.onCleared()
    }



}
package com.kadencelibrary.base

import android.app.Service
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Kenza on 04.06.2018.s
 */
abstract class BaseService : Service() {


     val disposables = CompositeDisposable()

    override fun onDestroy() {

        disposables.dispose()
        disposables.clear()

        super.onDestroy()
    }


}
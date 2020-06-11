package com.kadencelibrary.base

import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import io.reactivex.disposables.CompositeDisposable

open class KadenceDialog : androidx.fragment.app.DialogFragment() {



    val disposables = CompositeDisposable()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        disposables.dispose()
        disposables.clear()
        super.onDestroy()
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }



}
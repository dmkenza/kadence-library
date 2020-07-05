package com.kadencelibrary.base

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kadencelibrary.R
import com.kadencelibrary.extension.view.gone
import com.kadencelibrary.extension.view.vis
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.content_load.*


@SuppressLint("Registered")
open class KadenceActivity : AppCompatActivity() {

    val disposables = CompositeDisposable()

    var wasRestored = false

    var alertDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


    override fun onDestroy() {
        disposables.dispose()
        disposables.clear()
        alertDialog?.dismiss()
        super.onDestroy()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        wasRestored = true
    }


    fun <T : KadenceVm> getViewModel(clazz: Class<T>): T {
        return ViewModelProvider(this)[clazz]
    }


    /**
     * Set Layout with progress bar, what block all windows if show.
     */

    fun setLoadLayout() {

        val viewGroup = window.decorView.rootView as ViewGroup
        val load = View.inflate(this, R.layout.content_load, null)
        viewGroup.addView(load)

        load.setOnTouchListener { v, _ ->
            v.performClick()
            return@setOnTouchListener true
        }


        showBlockedLayout(false)
    }

    fun showBlockedLayout(show: Boolean = true, showProgressBar: Boolean = true) {
        container_loading?.vis(show)
        progressBar?.vis(showProgressBar)
    }

    fun back() {
        super.onBackPressed()
    }






//    fun showSystemUI() {
//        window.decorView.setSystemUiVisibility(
//            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        )
//    }

    fun alert(title: String = "", msg: String = "") {

        alertDialog?.dismiss()

        alertDialog = AlertDialog.Builder(this).apply {
            if (title.isNotEmpty()) {
                setTitle(title)
            }
            if (msg.isNotEmpty()) {
                setMessage(msg)
            }
        }.create()

        alertDialog?.show()

    }





}


fun Context.toActivity(): KadenceActivity? {

    if (this == null)
        return null
    else if (this is KadenceActivity)
        return this as KadenceActivity?
    else if (this is ContextWrapper)
        return ((this as ContextWrapper).baseContext.toActivity())

    return null
}
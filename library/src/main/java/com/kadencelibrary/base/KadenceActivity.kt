package com.kadencelibrary.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.kadencelibrary.R
import com.kadencelibrary.extension.debug.toast
import com.kadencelibrary.extension.view.vis
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.content_load.*


@SuppressLint("Registered")
open class KadenceActivity : AppCompatActivity() {

    val disposables = CompositeDisposable()

    var wasRestored = false


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

    /**
     * Send backpressed event to inner fragments
     */

    override fun onBackPressed() {
        if (notifyFragments()) {

        } else {
            super.onBackPressed()
        }
    }

    private fun notifyFragments(): Boolean {
        val fragments: List<Fragment> = supportFragmentManager.fragments
        return fragments.mapNotNull { it as? KadenceFragment }.first().onBackPressed()

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
package com.kadencelibrary.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kadencelibrary.R
import com.kadencelibrary.extension.view.vis
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.content_load.*


@SuppressLint("Registered")
open class KadenceActivity : AppCompatActivity() {

//    val spotlightHelper: SpotlightHelper by lazy {
//        SpotlightHelper(this)
//    }
//
//
//    val eHelper: EHelper = EHelper(this)
//    var nav: AppNavigator = CoreLibrary.comp.nav
//    val bag = CompositeDisposable()

    val disposables = CompositeDisposable()


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


    override fun onDestroy() {
        disposables.dispose()
        disposables.clear()
        super.onDestroy()
    }




    fun <T : KadenceVm> getViewModel(clazz: Class<T>): T {
        return ViewModelProvider (this)[clazz]
    }


    /**
     * set Layout with progress bar, what block all windows if show
     */

    fun setLoadLayout(vm: KadenceVm) {

        val viewGroup = getWindow().getDecorView().getRootView() as ViewGroup
        val load = View.inflate(this, R.layout.content_load, null)
        viewGroup.addView(load)

        load.setOnTouchListener { v, event ->
            v.performClick()
            return@setOnTouchListener true
        }

//        vm.loadingSingleLifeData.observe(this) {
//            showBlockedLayout(it)
//        }


        showBlockedLayout(false)
    }

    fun showBlockedLayout(show: Boolean = true, showProgressBar: Boolean = true) {
        container_loading?.vis(show)
        progressBar?.vis(showProgressBar)
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
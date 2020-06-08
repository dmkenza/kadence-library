package com.kadencelibrary.extension.view

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager


/** Get current fragment on ViewPager. */
fun ViewPager.getCurrentFragment( view_pager_id : Int, supportFragmentManager: FragmentManager): Fragment? {
    val id = this.currentItem
    return supportFragmentManager.findFragmentByTag("android:switcher:" + view_pager_id.toString() + ":" + id)
}

fun Fragment.addOnWindowFocusChangeListener(callback: (hasFocus: Boolean) -> Unit) = view?.viewTreeObserver?.addOnWindowFocusChangeListener { callback.invoke(it) }



fun androidx.fragment.app.DialogFragment.show(
    activity: AppCompatActivity,
    tag: String? = null
): androidx.fragment.app.DialogFragment {
    if (tag == null) {
        this.show(
            activity.supportFragmentManager,
            "${this.toString()}_${System.currentTimeMillis()}"
        )
    } else {
        this.show(activity.supportFragmentManager, "${tag}")
    }
    return this
}

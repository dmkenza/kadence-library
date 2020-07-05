package com.kadencelibrary.extension.context

import android.app.Activity
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


/**
 * Return height of current activity in pixel.
 */
fun Activity.getWindowHeight(): Int {
    val display = (this as Activity).windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)

    return size.y
}

/**
 * Return width of current activity in pixel.
 */
fun Activity.getWindowWidth(): Int {
    val display = (this as Activity).windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)

    return size.x
}



fun AppCompatActivity.replaceFragment(layout: Int, fragment: Fragment) {

    val transaction = this.getSupportFragmentManager().beginTransaction();
    transaction.replace(layout, fragment);
    transaction.commit();
}
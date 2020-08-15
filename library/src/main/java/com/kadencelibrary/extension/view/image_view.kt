package com.kadencelibrary.extension.view

import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 *  Set tint color, used resource color.
 */

fun ImageView.setTintColor(@ColorRes resColor: Int) {
    this.setColorFilter(
        ContextCompat.getColor(context, resColor),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
}
fun View.setBackgroundTintColor(@ColorRes resColor: Int) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.backgroundTintList = context.getResources().getColorStateList(resColor, null)
    }else{
        this.backgroundTintList = context.getResources().getColorStateList(resColor)
    }

}



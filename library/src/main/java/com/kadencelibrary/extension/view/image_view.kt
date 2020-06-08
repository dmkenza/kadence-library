package com.kadencelibrary.extension.view

import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 *  Set tint color, used resource color.
 */

fun ImageView.setTinColor(@ColorRes resColor: Int) {
    this.setColorFilter(
        ContextCompat.getColor(context, resColor),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
}

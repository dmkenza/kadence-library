package com.kadencelibrary.extension.view

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

fun View.setBias(bias: Float, isVertical: Boolean) {

    val params = this.getLayoutParams() as? ConstraintLayout.LayoutParams

    if(isVertical){
        params?.verticalBias = bias
    }else{
        params?.horizontalBias = bias
    }
    params?.let {
        this.layoutParams = params
    }
}
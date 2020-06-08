package com.kadencelibrary.extension.view

import android.view.View


/**
 * Set visibility of view
 */

fun View.vis(visible: Boolean = true) {

    if (visible) {
        if (this.visibility != View.VISIBLE) {
            this.visibility = View.VISIBLE
        }
    } else {
        this.visibility = View.GONE
    }


}

fun View.invis(invis: Boolean = true) {
    if (invis) {
        if (this.visibility != View.INVISIBLE) {
            this.visibility = View.INVISIBLE
        }

    } else {
        if (this.visibility != View.VISIBLE) {
            this.visibility = View.VISIBLE

        }
    }
}

fun View.gone(isGone: Boolean = true) {

    if (isGone) {
        this.visibility = View.GONE
    } else {
        if (this.visibility != View.INVISIBLE) {
            this.visibility = View.VISIBLE
        }
    }
}

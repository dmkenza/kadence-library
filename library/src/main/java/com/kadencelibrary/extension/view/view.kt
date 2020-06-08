package com.kadencelibrary.extension.view

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kadencelibrary.extension.text.getTimestamp


/** Convert seconds by pattern and put text. */

fun TextView.setTimeWithClock(timeDelay: Long) {
    var pattern = "mm:ss"

    if (timeDelay < 60 * 60) {
        pattern = "mm:ss"
    } else if (timeDelay >= 60 * 60) {
        pattern = "hh:mm:ss"
    }

    val parsed = "${(timeDelay * 1000).getTimestamp(pattern)}2"
//    val parsed = "${(timeDelay * 1000).getTimestamp(pattern)} \uD83D\uDD52"
//    this.setTextWithEmoji(parsed)
}


/**
 * Set color of ProgressBar.
 */

fun ProgressBar.setColor(@ColorRes colorId: Int) {
    val wrapDrawable = DrawableCompat.wrap(this.indeterminateDrawable)
    DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getContext(), colorId))
    this.indeterminateDrawable = DrawableCompat.unwrap(wrapDrawable)
}


fun RecyclerView.notifyDataSetChanged() {
    this.post {
        kotlin.runCatching { this.adapter?.notifyDataSetChanged() }
    }
}


/**
 *  Do action after view is drawn.
 */

inline fun View.afterMeasured(crossinline f: View.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}


fun View.setMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    lp.setMargins(
        left ?: lp.leftMargin, top ?: lp.topMargin, right ?: lp.rightMargin, bottom
            ?: lp.rightMargin
    )
    layoutParams = lp
}


fun View.setPadding(
    left: Int? = null,
    top: Int? = null,
    right: Int? = null,
    bottom: Int? = null
) {

    this.setPadding(
        left ?: this.paddingLeft,
        top ?: this.paddingTop,
        right ?: this.paddingRight,
        bottom ?: this.paddingBottom
    )

}


fun Int.getPxFromDp(context: Context): Int =
    (this * context.resources.displayMetrics.density).toInt()


fun View.setWidth(w: Int) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    lp.width = w
    layoutParams = lp
}


fun View.setHeight(h: Int) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    lp.height = h
    layoutParams = lp
}


/**
 * Set max height for view in ConstraintLayout container
 */

fun View.setMaxHeight(i: Int) {
    val lp = this.layoutParams as ConstraintLayout.LayoutParams
    lp.matchConstraintMaxHeight = i
    this.layoutParams = lp

}


fun MenuItem?.setColorMenuItem(context: Context, @ColorRes color: Int) = this?.icon?.let {
    DrawableCompat.setTint(it, ContextCompat.getColor(context, color))
}


fun RecyclerView.isLastItemVisible(): Boolean {

    if (this.adapter?.itemCount != 0) {
        val lastVisibleItemPosition =
            (this.getLayoutManager() as LinearLayoutManager).findLastVisibleItemPosition();

        if (lastVisibleItemPosition == (this.getAdapter()?.getItemCount() ?: 0) - 1) {
            return true;
        }
    }
    return false;

}

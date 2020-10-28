package com.kadencelibrary.extension.view

import android.os.Build
import android.text.Html
import android.text.InputFilter
import android.widget.TextView
import com.kadencelibrary.extension.text.getColoredHTMLText
import java.lang.ref.WeakReference


fun TextView.setHTMLText(text: String, color: String) {
    val colored = getColoredHTMLText(text, color)
    this.setHTMLText(colored)
}


/**
 * Set text with html tags to textView.
 */

fun TextView.setHTMLText(textWithHtml: String) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.setText(
            Html.fromHtml(textWithHtml, Html.FROM_HTML_MODE_COMPACT),
            TextView.BufferType.NORMAL
        )
    } else {
        this.setText(Html.fromHtml(textWithHtml), TextView.BufferType.NORMAL)
    }


/**
 * Set all text on Upper case
 */

fun TextView.setAllCapsFilter() {

    val list = this.filters.toMutableList()
    list.add(InputFilter.AllCaps())
    this.setFilters(list.toTypedArray())
}


/** Support emoji, fix some device don't show emoji correctly.  */
//fun TextView.setTextWithEmoji(text: String) {
//
//    val weakTv = WeakReference<TextView>(this)
//    EmojiCompat.get().registerInitCallback(object : EmojiCompat.InitCallback() {
//        override fun onInitialized() {
//            val compat = EmojiCompat.get()
//            weakTv.get()?.text = compat.process(text)
//        }
//    })
//}

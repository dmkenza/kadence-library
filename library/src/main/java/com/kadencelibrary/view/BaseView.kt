package com.kadencelibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import io.reactivex.disposables.CompositeDisposable
import android.content.ContextWrapper
import android.app.Activity
import com.kadencelibrary.R
import com.kadencelibrary.extension.context.getColorCompat



open class BaseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    val disposables = CompositeDisposable()


    init {
        if(!isInEditMode){

        }
    }

    fun getActivity(): Activity? {
        var context = context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }


    /** Set placeholder for complex view in preview mode. */

    inline fun BaseView.setttingPreview(title: String) {

        val lot = LinearLayout(context)
        lot.setLayoutParams(
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )

        lot.setGravity(Gravity.CENTER)

        val text = TextView(context)
        text.setGravity(Gravity.CENTER);
        text.setText(title)
        lot.addView(text)
        lot.setBackgroundColor(context.getColorCompat(R.color.medium_purple))
        addView(lot)


    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposables.clear()
        disposables.dispose()
    }
}
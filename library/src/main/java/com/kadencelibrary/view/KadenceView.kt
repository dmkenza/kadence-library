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
import android.widget.FrameLayout
import com.kadencelibrary.R
import com.kadencelibrary.extension.context.getColorCompat
import com.kadencelibrary.extension.debug.toast


open class KadenceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val disposables = CompositeDisposable()


    init {
        if(!isInEditMode){

        }
    }


    fun toast(msg : String){
        if (!isInEditMode) {
            toast(context, msg)
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

    fun setttingPreview(title: String) {


        if (!isInEditMode()) {
            return
        }

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
package com.kadencelibrary.view

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kadencelibrary.R
import com.kadencelibrary.extension.context.getColorCompat
import com.kadencelibrary.extension.view.setBackgroundTintColor
import kotlinx.android.synthetic.main.custom_rounded_view.view.*

/**
 * Class define circled view with explicit borders
 */
class RoundedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var colorDefault: Int = 0
    private var showHiddedText = true

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_rounded_view, this, true)
    }


    fun setCircleColor(@ColorRes colorRes : Int  ) {
        layout_border.setBackgroundTintColor(colorRes)
    }


    fun setCirclePadding(padding: Int ) {
        layout_border.setPadding(padding, padding, padding,padding)
    }


    fun setUrlImage(url: String) {
        /** create circle placeholder **/

//        val placeholder = DrawableHelper.createDrawable(R.drawable.ic_placeholder_user)
//        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context?.resources!!, placeholder)
//        circularBitmapDrawable.cornerRadius = max(placeholder.width, placeholder.height) / 1f

        /**  load circle image **/

        val requestOptions = RequestOptions()//.placeholder(placeholder)

        Glide.with(this)
            .setDefaultRequestOptions(requestOptions)
            .load(url)
//                .load("https://in-rating.ru/space/wp-content/uploads/2016/12/%D0%BE%D0%B1%D0%BE%D0%B8-%D0%B7%D0%B8%D0%BC%D0%B0-%D0%B4%D0%B5%D0%B2%D1%83%D1%88%D0%BA%D0%B0-09_m.jpg")
//                .load("https://www.firestock.ru/wp-content/uploads/2013/10/firestock_woman_29092013-700x756.jpg")
            .apply(RequestOptions.circleCropTransform())
            .into(img)
    }

}
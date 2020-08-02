package com.kadencelibrary.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import java.util.*

/** Help generate drawable with ripple effect  */

object DrawablesCreaterUtil {


    fun getRectangle(
        color: Int
    ): Drawable {


        val background = ColorDrawable()
        background.setColor(color)
        return background

    }


    fun getDrawableFor(
        color: Int,
        cornerRadius: Float,
        borderColor: Int? = null,
        borderWidth: Int? = null
    ): Drawable {


        val background = GradientDrawable()
        background.setShape(GradientDrawable.RECTANGLE)
        background.setColor(color)

        background.setCornerRadius(cornerRadius)

        borderColor?.let {
            background.setStroke(borderWidth!!, borderColor)
        }

        return background

    }


    fun Drawable.getSelectableDrawableFor(
        color: Int,
        cornerRadius: Float,
        borderColor: Int? = null,
        borderWidth: Int? = null
    ): Drawable {


        val background = GradientDrawable()
        background.setShape(GradientDrawable.RECTANGLE)
        background.setColor(color)

        background.setCornerRadius(cornerRadius)

        borderColor?.let {
            background.setStroke(borderWidth!!, borderColor)
        }


        val mask = GradientDrawable()
        mask.setShape(GradientDrawable.RECTANGLE)
        mask.setColor(-0x1000000)
        mask.setCornerRadius(cornerRadius)


//        val rippleColorLst = ColorStateList.valueOf(
//                Color.argb(255, 50, 150, 255)
//        )

        val rippleColorLst = ColorStateList.valueOf(lightenOrDarken(color, 0.2))

        val ripple = RippleDrawable(rippleColorLst, background, mask)
        return ripple

    }


    private fun getRippleColor(color: Int): Drawable {
        val outerRadii = FloatArray(8)
        Arrays.fill(outerRadii, 3f)
        val r = RoundRectShape(outerRadii, null, null)
        val shapeDrawable = ShapeDrawable(r)
        shapeDrawable.paint.color = color
        return shapeDrawable
    }

    private fun lightenOrDarken(color: Int, fraction: Double): Int {
        return if (canLighten(color, fraction)) {
            lighten(color, fraction)
        } else {
            darken(color, fraction)
        }
    }

    private fun lighten(color: Int, fraction: Double): Int {
        var red = Color.red(color)
        var green = Color.green(color)
        var blue = Color.blue(color)
        red = lightenColor(red, fraction)
        green = lightenColor(green, fraction)
        blue = lightenColor(blue, fraction)
        val alpha = Color.alpha(color)
        return Color.argb(alpha, red, green, blue)
    }

    private fun darken(color: Int, fraction: Double): Int {
        var red = Color.red(color)
        var green = Color.green(color)
        var blue = Color.blue(color)
        red = darkenColor(red, fraction)
        green = darkenColor(green, fraction)
        blue = darkenColor(blue, fraction)
        val alpha = Color.alpha(color)
        return Color.argb(alpha, red, green, blue)
    }

    private fun canLighten(color: Int, fraction: Double): Boolean {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return (canLightenComponent(red, fraction)
                && canLightenComponent(green, fraction)
                && canLightenComponent(blue, fraction))
    }

    private fun canLightenComponent(colorComponent: Int, fraction: Double): Boolean {
        val red = Color.red(colorComponent)
        val green = Color.green(colorComponent)
        val blue = Color.blue(colorComponent)
        return red + red * fraction < 255 && green + green * fraction < 255 && blue + blue * fraction < 255
    }

    private fun darkenColor(color: Int, fraction: Double): Int {
        return Math.max(color - color * fraction, 0.0).toInt()
    }

    private fun lightenColor(color: Int, fraction: Double): Int {
        return Math.min(color + color * fraction, 255.0).toInt()
    }
}
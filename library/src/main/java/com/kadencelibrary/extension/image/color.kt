package com.kadencelibrary.extension.image

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

/**
 * Set color of drawable.
 */
fun Drawable.setColor(context: Context, @ColorRes colorId: Int) {

    val iColor = ContextCompat.getColor(context, colorId);

    val red = (iColor and 0xFF0000) / 0xFFFF
    val green = (iColor and 0xFF00) / 0xFF
    val blue = iColor and 0xFF

    val matrix = floatArrayOf(
        0f,
        0f,
        0f,
        0f,
        red.toFloat(),
        0f,
        0f,
        0f,
        0f,
        green.toFloat(),
        0f,
        0f,
        0f,
        0f,
        blue.toFloat(),
        0f,
        0f,
        0f,
        1f,
        0f
    )

    val colorFilter: ColorFilter = ColorMatrixColorFilter(matrix)
    this.colorFilter = colorFilter
}




/** Get image from drawable's vector. */
fun Context.getBitmapFromVectorDrawable(drawable: Drawable): Bitmap? {
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    ) ?: return null
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}

fun Drawable.getBitmapFromVectorDrawable(): Bitmap {
    val bitmap =
        Bitmap.createBitmap(this!!.intrinsicWidth, this.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    this.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
    this.draw(canvas)
    return bitmap
}

fun Bitmap.getResizedBitmap(newWidth: Int, newHeight: Int): Bitmap? {
    val width = this.width
    val height = this.height
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    val resizedBitmap = Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
    this.recycle()
    return resizedBitmap
}

/** add ripple_semi_rectangle  effect to drawable */
fun Drawable.getRippleBackgroundDrawable(pressedColor: Int): RippleDrawable {
    return RippleDrawable(getPressedState(pressedColor)!!, this, null)
}

private fun getPressedState(pressedColor: Int): ColorStateList? =
    ColorStateList(arrayOf(intArrayOf()), intArrayOf(pressedColor))


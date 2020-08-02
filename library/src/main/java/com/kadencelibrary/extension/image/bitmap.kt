package com.kadencelibrary.extension.image

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import com.kadencelibrary.extension.context.toBitmap
import java.io.File

/**
 * some images may be rotated by camera. This fix it.
 */

fun File.toBitmapFixOrientation(): Bitmap? {
    var image = this.toBitmap()
    var rotate = 0
    try {
        val exif = ExifInterface(this.getAbsolutePath())
        val orientation: Int = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
            ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
            ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
            ExifInterface.ORIENTATION_NORMAL -> rotate = 0
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    if (rotate != 0) {
        val matrix = Matrix()
        matrix.postRotate(rotate.toFloat())
        image = Bitmap.createBitmap(
            image, 0, 0, image.width,
            image.height, matrix, true
        )
    }
    return image
}

fun File.isBitmapRotated90(): Boolean {
    var rotate = 0
    try {
        val exif = ExifInterface(this.getAbsolutePath())
        val orientation: Int = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        return  when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_270 -> true
            ExifInterface.ORIENTATION_ROTATE_180 -> false
            ExifInterface.ORIENTATION_ROTATE_90 -> true
            ExifInterface.ORIENTATION_NORMAL -> false
            else -> false
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return false
}
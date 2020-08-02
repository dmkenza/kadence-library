package com.kadencelibrary.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.view.Surface
import android.view.WindowManager

/**
 * This class is used to lock orientation of android app in nay android devices
 */
object OrientationUtils {
    /**
     * Locks the device window in landscape mode.
     */
    fun lockOrientationLandscape(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    }

    /**
     * Locks the device window in portrait mode.
     */
    fun lockOrientationPortrait(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * Locks the device window in actual screen mode.
     */
    @SuppressLint("ObsoleteSdkInt")
    fun lockOrientation(activity: Activity) {
        val orientation = activity.resources.configuration.orientation
        val rotation =
            (activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
                .rotation

        // Copied from Android docs, since we don't have these values in Froyo
        // 2.2
        var SCREEN_ORIENTATION_REVERSE_LANDSCAPE = 8
        var SCREEN_ORIENTATION_REVERSE_PORTRAIT = 9

        // Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            SCREEN_ORIENTATION_REVERSE_LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            SCREEN_ORIENTATION_REVERSE_PORTRAIT = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        } else if (rotation == Surface.ROTATION_180 || rotation == Surface.ROTATION_270) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
            }
        }
    }

    /**
     * Unlocks the device window in user defined screen mode.
     */
    fun unlockOrientation(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
    }
}
package com.kadencelibrary.extension.touch

import android.view.MotionEvent
import android.view.View


/**
 * Class handle taps on view.
 * Execute callback when taps will enough.
 *
 * @param taps - How many times need tap on selected area.
 * @param tapZones - Split touch area on own horizontal zones.
 * @param function - Executed function.
 */

fun View.onTap(taps: Int, tapZones: Int = 1, function: (zone: Int) -> Unit) {


    class OnTouchsAdapter(val count: Int, var zone: Int, val callback: (zone: Int) -> Unit) :
        OnTouchMultipleTapListener(tapZones = zone) {

        override fun onMultipleTapEvent(e: MotionEvent, numberOfTaps: Int, zone: Int) {

            if (numberOfTaps >= count) {
                callback(zone)
            }
        }
    }


    this.setOnTouchListener(OnTouchsAdapter(taps, tapZones, function))
}

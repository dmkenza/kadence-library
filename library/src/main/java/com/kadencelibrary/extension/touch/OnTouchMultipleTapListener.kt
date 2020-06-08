package com.kadencelibrary.extension.touch

import android.os.Handler
import android.view.ViewConfiguration
import android.view.MotionEvent
import android.view.View
import kotlin.math.roundToInt

/**
 * Class define Listener for handle multiple taps.
 *
 * @param onTouchDown - Calculate touches only for ACTION_DOWN if true.
 * @param tapZones - Split touch area on own horizontal zones.
 *
 */

abstract class OnTouchMultipleTapListener @JvmOverloads constructor(
    val onTouchDown: Boolean = false,
    val tapZones: Int = 1
) : View.OnTouchListener {


    val tapTimeoutMultiplier: Float = 1f

    internal var handler = Handler()

    private var numberOfTaps = 0
    private var lastTapTimeMs: Long = 0
    private var touchDownMs: Long = 0


    abstract fun onMultipleTapEvent(e: MotionEvent, numberOfTaps: Int, zone: Int)


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (onTouchDown) {
            onTouchDownManagement(v, event)
        } else {
            onTouchUpManagement(v, event)
        }
        return true
    }


    private fun onTouchDownManagement(v: View, event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                touchDownMs = System.currentTimeMillis()

                handler.removeCallbacksAndMessages(null)

                if (numberOfTaps > 0 && System.currentTimeMillis() - lastTapTimeMs < ViewConfiguration.getTapTimeout() * tapTimeoutMultiplier) {
                    numberOfTaps += 1
                } else {
                    numberOfTaps = 1
                }

                lastTapTimeMs = System.currentTimeMillis()

                if (numberOfTaps > 0) {
                    val finalMotionEvent = MotionEvent.obtain(event) // to avoid side effects
                    val zone = getTapZone(v, event)
                    handler.postDelayed(Runnable {
                        onMultipleTapEvent(
                            finalMotionEvent,
                            numberOfTaps,
                            zone
                        )
                    }, (ViewConfiguration.getDoubleTapTimeout() * tapTimeoutMultiplier).toLong())
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }
    }


    private fun onTouchUpManagement(v: View, event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchDownMs = System.currentTimeMillis()
            MotionEvent.ACTION_UP -> {
                handler.removeCallbacksAndMessages(null)

                if (System.currentTimeMillis() - touchDownMs > ViewConfiguration.getTapTimeout()) {
                    numberOfTaps = 0
                    lastTapTimeMs = 0
                }

                if (numberOfTaps > 0 && System.currentTimeMillis() - lastTapTimeMs < ViewConfiguration.getDoubleTapTimeout()) {
                    numberOfTaps += 1
                } else {
                    numberOfTaps = 1
                }

                lastTapTimeMs = System.currentTimeMillis()

                if (numberOfTaps > 0) {
                    val finalMotionEvent = MotionEvent.obtain(event) // to avoid side effects

                    val zone = getTapZone(v, event)

                    handler.postDelayed(Runnable {
                        onMultipleTapEvent(
                            finalMotionEvent,
                            numberOfTaps,
                            zone
                        )
                    }, ViewConfiguration.getDoubleTapTimeout().toLong())
                }
            }
        }
    }


    fun getTapZone(v: View, event: MotionEvent?): Int {


        val h = v.height
        val w = v.width

        val x = event?.x ?: 0f
        val y = event?.y ?: 0f


        val d = w / tapZones

        var zone = 0

        if (x < d) {
            return zone
        }

        zone = (x.toDouble() / d).roundToInt()
        return zone

    }

}
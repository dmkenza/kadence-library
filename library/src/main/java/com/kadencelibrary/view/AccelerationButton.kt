package com.kadencelibrary.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import com.kadencelibrary.R
import com.kadencelibrary.extension.context.getColorCompat
import com.kadencelibrary.extension.view.setBackgroundTintColor
import com.kadencelibrary.extension.view.setTintColor
import com.kadencelibrary.utils.RippleDrawableHelper
import kotlinx.android.synthetic.main.custom_acceleration_button.view.*


open class AccelerationButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    open var parentIflate = true

    interface AccelerationButtonDelegate {
        fun onValueRequested(): Long
        fun onValueChanged(newValue: Long, oldValue: Long)
    }

    init {

        if (parentIflate) {
            val root2 = LayoutInflater.from(context)
                .inflate(R.layout.custom_acceleration_button, this, false)
            addView(root2)


//            bt_minus.setOnClickListener {
//
//            }
//
//            bt_plus.setOnClickListener {
//
//            }


            val b1: View = findViewById(R.id.bt_minus)
//            val v2: View = findViewById(R.id.bt_minus) //the are where the ripple_semi_rectangle effect extends

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                b1.background = RippleDrawableHelper.createRippleDrawable(
                    b1,
                    context.getColorCompat(R.color.athens_gray)
                )
            }


            val b2: View = findViewById(R.id.bt_plus)
//            val v2: View = findViewById(R.id.bt_minus) //the are where the ripple_semi_rectangle effect extends

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                b2.background = RippleDrawableHelper.createRippleDrawable(
                    b2,
                    context.getColorCompat(R.color.athens_gray)
                )
            }


        }
    }



    fun setColorLeftButton(@ColorRes colorId: Int ){
        bt_minus.setBackgroundTintColor(colorId)
    }

    fun setColorRightButton(@ColorRes colorId: Int ){
        bt_plus.setBackgroundTintColor(colorId)
    }


    var contract: AccelerationButtonDelegate? = null


    var max: Long = 1000L
    var min: Long = 0L

    var direct = -1
    var count = 0

    @SuppressLint("ClickableViewAccessibility")
    fun setDelegates(contract: AccelerationButtonDelegate, maxValue: Long, minValue: Long) {

        this.contract = contract


        this.max = maxValue
        this.min = minValue

        bt_minus.setOnClickListener {

            if (contract.onValueRequested() - 1 > min) {
                val value = contract.onValueRequested()
                contract.onValueChanged(value - 1, value)
            }

        }


        bt_plus.setOnClickListener {

            if (contract.onValueRequested() + 1 < max) {
                val value = contract.onValueRequested()
                contract.onValueChanged(value + 1, value)
            }
        }


        bt_plus.setOnTouchListener { _, event ->

            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    direct = 1
                    count = 0
                    timer.start()
                }

                MotionEvent.ACTION_UP -> {
                    timer.cancel()
                }

                MotionEvent.ACTION_MOVE -> {

                }
                else -> {
                    timer.cancel()

                }
            }


            return@setOnTouchListener false
        }


        bt_minus.setOnTouchListener { v, event ->

            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    direct = -1
                    count = 0
                    timer.start()
                }

                MotionEvent.ACTION_UP -> {
                    timer.cancel()
                }

                MotionEvent.ACTION_MOVE -> {

                }
                else -> {
                    timer.cancel()

                }
            }


            return@setOnTouchListener false
        }

    }

    var timer = object : CountDownTimer(360000, 200) {

        override fun onTick(millisUntilFinished: Long) {

            var factor = 1

            if (count <= 5) {
                factor = 1
            } else if (count <= 7) {
                factor = 2
            } else if (count <= 12) {
                factor = 10
            } else if (count <= 17) {
                factor = 20
            } else {
                factor = 50
            }


            count++
            var dx = 1 * factor * direct

            var y = contract?.onValueRequested() ?: 0

            if (y + dx < min) {
                dx = min.toInt() - y.toInt()
            }

            if (y + dx >= min && dx < 0) {
//                onChangeDataInvoker?.invoke(dx)

                val value = contract?.onValueRequested() ?: 0L
                contract?.onValueChanged(value + dx, value)
            }

            if (y + dx > max) {
                dx = max.toInt() - y.toInt()
            }

            if (y + dx <= max && dx > 0) {
                val value = contract?.onValueRequested() ?: 0L
                contract?.onValueChanged(value + dx, value)
            }

        }

        override fun onFinish() {
            this.start()
        }
    }


}
package com.kadencelibrary.view

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.PopupWindow
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.AppCompatImageView
import com.kadencelibrary.R


class SeekbarWithIndicator : AppCompatSeekBar, SeekBar.OnSeekBarChangeListener {

    private var indicatorFrameWidth = 0F
    private var indicatorFrameHeight = 0F
    private var indicatorFrameMargin = 0F
    private var indicatorEnabled = true

    private var indicator: PopupWindow? = null
    private var textIndicator: AppCompatTextView? = null
    private var imageIndicator: AppCompatImageView? = null
    private var seekbarChangeListener: OnSeekBarChangeListener? = null


    fun setIndicatorEnable(enabled: Boolean){
        indicatorEnabled = enabled
    }


    interface OnSeekBarChangeListener {
        fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean)

    }

    constructor(context: Context): super(context) {
        init(context, null,0)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }


    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        setOnSeekBarChangeListener(this)
        val parent = View.inflate(context, R.layout.seekbar_with_indicator, null)

        indicatorFrameWidth = 150F
        indicatorFrameHeight = 150F
        textIndicator = parent.findViewById(R.id.text)
        imageIndicator = parent.findViewById(R.id.image)
        indicator = PopupWindow(parent, 150, 150, false)

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.SeekbarWithIndicator,
            defStyle, 0)
        /**
         * Задаем стиль анимации
         * defValue = 0
         */
        indicator?.animationStyle = typedArray.getResourceId(
            R.styleable.SeekbarWithIndicator_indicatorStyleAnimation,0)

        /**
         * Задаем отступ индикатора от SeekBar
         * defValue = 30F
         */
        indicatorFrameMargin = typedArray.getFloat(
            R.styleable.SeekbarWithIndicator_indicatorMargin,
            30F)

        /**
         * Задаем цвет текста на индикаторе
         * defValue = Color.WHITE
         */
        textIndicator?.setTextColor(typedArray.getColor(
            R.styleable.SeekbarWithIndicator_indicatorTextColor,
            Color.WHITE))

        /**
         * Задаем ресурс изображения для индикатора
         * defValue = R.drawable.ic_indicator
         */
        imageIndicator?.setImageResource(typedArray.getResourceId(
            R.styleable.SeekbarWithIndicator_indicatorImageSource,
            R.drawable.ic_indicator))

    }

    fun setCustomSeekbarOnChangeListener(listener: OnSeekBarChangeListener?) {
        seekbarChangeListener = listener
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        seekbarChangeListener?.onProgressChanged(p0!!, p1, p2)
        textIndicator!!.text =  progress.toString()
        indicator?.update(p0, getXPosition(p0!!), getYPosition(p0), WRAP_CONTENT, WRAP_CONTENT)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        if (indicator?.isShowing == false && indicatorEnabled) {
            textIndicator!!.text =  progress.toString()
            indicator?.showAsDropDown(this, getXPosition(this), getYPosition(this))
        }
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        if (indicator?.isShowing == true && indicatorEnabled) {
            indicator?.dismiss()
        }
    }

    private fun getXPosition(seekBar: SeekBar): Int {
        val width = Math.round(seekBar.width.toDouble()) - seekBar.paddingLeft - seekBar.paddingRight
        val seekMax = if (seekBar.max == 0) seekBar.progress else seekBar.max
        val thumbPos = seekBar.paddingLeft + width * seekBar.progress / seekMax
        return thumbPos.toInt() - indicatorFrameWidth.toInt() / 2
    }

    private fun getYPosition(seekBar: SeekBar): Int {
        return -(seekBar.height + indicatorFrameHeight.toInt() + indicatorFrameMargin.toInt())
    }

}
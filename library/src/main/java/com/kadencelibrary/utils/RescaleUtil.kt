package com.kadencelibrary.utils


/**
 * Help convert range [x1,x2] to [y1, y2] and get interpolated value.
 *
 * Example:
        val r = Rescale(5,7,6,0,1);
        Assert.assertTrue(r.rescale(5) == 0);
        Assert.assertTrue(r.rescale(6) == 0.5);
        Assert.assertTrue(r.rescale(7) == 1);
 *
 */
class RescaleUtil(
    private val domain1: Float,
    private val domain2: Float,
    private val centerRange: Float,
    private val range1: Float,
    private val range2: Float
) {
    private fun interpolate(x: Float, rangeMin: Float, rangeMax: Float): Float {
        return rangeMin * (1 - x) + rangeMax * x
    }


    // to percent x to 0..1
    private fun uninterpolate(x: Float,  min : Float, max: Float): Float {
        val b = if (max - min != 0f) max - min else 1 / max
        return (x - min) / b
    }

    fun rescale(x: Float): Float {

        val percent = uninterpolate(x, domain1, domain2)

        val midle = if(percent > 0.5f){
            interpolate((percent - 0.5f) * 2f, centerRange, range2)
        }else{
            interpolate(percent * 2f, range1, centerRange)
        }

        return midle
    }

}
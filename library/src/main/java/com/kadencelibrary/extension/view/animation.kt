package com.kadencelibrary.extension.view

import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import io.reactivex.Completable

/**
 * Animation functions for View.
 */



fun View.moveUp( duration: Long = 1000, offset: Float = 0f): Completable {
    return Completable.create {
        var offset = offset


        if (offset == 0f) {
            offset = this.height * 1f
        }

        visibility = View.VISIBLE
        translationY = offset
        translationX = 0f


        animate()
            .translationY(0f)
            .setDuration(duration)
            .setInterpolator(LinearInterpolator())
            .withEndAction(it::onComplete)

    }
}


fun View.moveDown( duration: Long = 1000, offset: Float = 0f): Completable {
    return Completable.create {
        var offset = offset

        if (offset == 0f) {
            offset = this.height * 1f
        }


        visibility = View.VISIBLE
        translationY = 0f
        translationX = 0f



        animate()
            .translationY(offset)
            .setDuration(duration)
            .setInterpolator(LinearInterpolator())
            .withEndAction(it::onComplete)
    }
}


fun View.slideIn( duration: Long = 1000, offset: Float = 0f): Completable {
    return Completable.create {

        var offset = offset

        visibility = View.VISIBLE

        if (offset == 0f) {
            offset = this.height * 1f
        }

        alpha = 0f
        scaleX = 0f
        scaleY = 0f

        translationY = offset
        translationX = 0f

        animate().alpha(1f)
            .translationY(0f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(duration)
            .setInterpolator(LinearInterpolator())
            .withEndAction(it::onComplete)
    }
}


fun View.slideLeft(offset: Float): Completable {
    return Completable.create {
        visibility = View.VISIBLE

        translationX = 0f
        alpha = 1f
        scaleX = 1f
        scaleY = 1f

        animate().alpha(0f)
            .translationX(-offset)
            .scaleX(0f)
            .scaleY(0f)
            .setDuration(200)
            .setInterpolator(LinearInterpolator())
            .withEndAction(it::onComplete)
    }
}

fun View.slideRight(offset: Float): Completable {
    return Completable.create {
        visibility = View.VISIBLE

        translationX = 0f
        alpha = 1f
        scaleX = 1f
        scaleY = 1f

        animate().alpha(0f)
            .translationX(offset)
            .scaleX(0f)
            .scaleY(0f)
            .setDuration(200)
            .setInterpolator(LinearInterpolator())
            .withEndAction(it::onComplete)
    }
}


fun View.slideOut(offset: Float): Completable {
    return Completable.create {
        animate().alpha(0f)
            .scaleX(0f)
            .scaleY(0f)
            .translationY(offset)
            .setDuration(200)
            .withEndAction {
                visibility = View.GONE
                it.onComplete()
            }
    }
}

fun View.fadeOut(duration: Long = 30): Completable {
    return Completable.create {
        animate().setDuration(duration)
            .alpha(0f)
            .withEndAction {
                visibility = View.GONE
                it.onComplete()
            }
    }
}

fun View.fadeIn(duration: Long = 30): Completable {
    return Completable.create {
        visibility = View.VISIBLE
        alpha = 0f
        animate().alpha(1f)
            .setDuration(duration)
            .withEndAction(it::onComplete)
    }
}

fun View.rotate(degree: Float): Completable {
    return Completable.create {
        animate().rotation(degree)
            .setDuration(200)
            .withEndAction(it::onComplete)
    }
}






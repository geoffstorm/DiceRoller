package com.gstormdev.diceroller.util

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

object AnimationUtil {

    const val ENTRANCE_DURATION = 250L
    const val EXIT_DURATION = 200L
    const val DEFAULT_DURATION = ENTRANCE_DURATION

    fun expand(container: View, expandable: View): Animator {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(container.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        expandable.measure(widthSpec, heightSpec)
        val targetHeight = expandable.measuredHeight
        return ValueAnimator.ofInt(0, targetHeight).apply {
            duration = ENTRANCE_DURATION
            interpolator = FastOutSlowInInterpolator()
            doOnStart { expandable.isVisible = true }
            addUpdateListener { updatedAnimation ->
                expandable.layoutParams.height = if (updatedAnimation.animatedFraction == 1f) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    updatedAnimation.animatedValue as Int
                }
                expandable.requestLayout()
            }
        }
    }

    fun collapse(collapsible: View): Animator {
        val initialHeight = collapsible.measuredHeight
        return ValueAnimator.ofInt(initialHeight, 0).apply {
            duration = EXIT_DURATION
            interpolator = FastOutSlowInInterpolator()
            doOnEnd {
                collapsible.isVisible = false
                collapsible.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            addUpdateListener { updatedAnimation ->
                collapsible.layoutParams.height = updatedAnimation.animatedValue as Int
                collapsible.requestLayout()
            }
        }
    }

    fun rotate180(view: View, clockwise: Boolean): Animator {
        val initialRotation = view.rotation
        var rotationAngle = 180
        if (!clockwise) rotationAngle *= -1
        return ValueAnimator.ofFloat(initialRotation, initialRotation + rotationAngle).apply {
            duration = DEFAULT_DURATION
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener { updatedAnimation ->
                view.rotation = updatedAnimation.animatedValue as Float
            }
        }
    }
}
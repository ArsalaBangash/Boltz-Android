package com.arsalabangash.boltz.practice.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View

fun ValueAnimator.onAnimStart(onStart: () -> Unit) {
    this.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) {
        }

        override fun onAnimationEnd(p0: Animator?) {
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationStart(p0: Animator?) {
            onStart.invoke()
        }

    })
}

fun ValueAnimator.onAnimEnd(onEnd: () -> Unit) {
    this.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) {
        }

        override fun onAnimationEnd(p0: Animator?) {
            onEnd.invoke()
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationStart(p0: Animator?) {
        }

    })
}

fun animateInfinite(view: View, property: String): ObjectAnimator {
    val animator = ObjectAnimator.ofInt(view, property, 0, 100000)
    animator.duration = 1000
    animator.repeatCount = ObjectAnimator.INFINITE
    animator.repeatMode = ObjectAnimator.RESTART
    animator.start()
    return animator
}
package com.arsalabangash.boltz.practice.utils

import android.animation.Animator
import android.animation.ValueAnimator

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
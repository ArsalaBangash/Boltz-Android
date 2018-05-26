package com.arsalabangash.boltz.practice.utils

import android.animation.Animator
import com.airbnb.lottie.LottieAnimationView

fun LottieAnimationView.onAnimEnd(onEnd: () -> Unit) {
    this.addAnimatorListener(object : Animator.AnimatorListener {
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
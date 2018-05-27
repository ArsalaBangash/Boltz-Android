package com.arsalabangash.boltz.practice.ui.fragments


import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.ui.activities.BoltzPracticeActivity
import com.arsalabangash.boltz.practice.utils.onAnimEnd
import kotlinx.android.synthetic.main.fragment_answer_feedback.view.*


data class AnimationData(val speed: Float, val scale: Float)

class AnswerFeedbackFragment : Fragment() {

    private lateinit var boltzPracticeActivity: BoltzPracticeActivity
    private lateinit var correctMP: MediaPlayer
    private lateinit var inCorrectMP: MediaPlayer
    private lateinit var sessionEndMP: MediaPlayer

    private lateinit var xpView: TextView
    private lateinit var animationContainer: FrameLayout
    private lateinit var correctFeedbackContainer: RelativeLayout
    private lateinit var incorrectFeedbackContainer: FrameLayout

    private lateinit var checkAnim: LottieAnimationView

    val animations = hashMapOf<String, AnimationData>(
            "balloons.json" to AnimationData(6f, 1f),
            "clap.json" to AnimationData(0.8f, 2f),
            "correct_check.json" to AnimationData(1f, 0.5f),
            "done.json" to AnimationData(2f, 0.5f)
    )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragmentView = inflater.inflate(R.layout.fragment_answer_feedback, container, false)
        boltzPracticeActivity = activity as BoltzPracticeActivity
        correctMP = MediaPlayer.create(boltzPracticeActivity, R.raw.correct)
        inCorrectMP = MediaPlayer.create(boltzPracticeActivity, R.raw.incorrect)
        sessionEndMP = MediaPlayer.create(activity, R.raw.session_end)
        bindViews(fragmentView)
        return fragmentView
    }

    fun bindViews(fragmentView: View) {
        xpView = fragmentView.feedback_xp
        animationContainer = fragmentView.feedback_animation_container
        correctFeedbackContainer = fragmentView.feedback_correct
        incorrectFeedbackContainer = fragmentView.feedback_incorrect
        checkAnim = fragmentView.feedback_anim_check
    }

    fun correctAnswer(xp: Long) {
        if (boltzPracticeActivity.getPracticeOptions().isVolumeOn) {
            correctMP.start()
        }
        correctFeedbackContainer.alpha = 1f
        animateCorrect(xp)
    }

    fun incorrectAnswer() {
        if (boltzPracticeActivity.getPracticeOptions().isVolumeOn) {
            inCorrectMP.start()
        }
        incorrectFeedbackContainer.alpha = 1f
        incorrectFeedbackContainer.animate().alpha(0f).duration = 800
    }

    fun endSession(didComplete: Boolean) {
        if (didComplete) {
            sessionEndMP.start()
        }
    }

    private fun animateCorrect(xp: Long) {
        animateXP(xp)
        setCorrectAnimation("correct_check.json", checkAnim)

    }

    private fun setCorrectAnimation(animation: String, animationView: LottieAnimationView) {
        val animationData = animations[animation]!!
        animationView.scale = animationData.scale
        animationView.setSpeed(animationData.speed)
        animationView.progress = 0f
        animationView.playAnimation()
        animationView.onAnimEnd {
            correctFeedbackContainer.animate().alpha(0f)
            animationContainer.animate().alpha(0f)
            boltzPracticeActivity.answerFeedbackFinished()
        }
    }

    private fun animateXP(xp: Long) {
        val xpString = StringBuilder("+")
        xpString.append(xp)
        xpString.append(" xp")
        xpView.text = xpString.toString()
        xpView.animate().alpha(1f).setDuration(100)
        xpView.animate().scaleX(2.5f).setDuration(300)
        xpView.animate().scaleY(2.5f).setDuration(300)
        val xpTextHideAnim = ObjectAnimator.ofPropertyValuesHolder(xpView,
                PropertyValuesHolder.ofFloat(View.ALPHA, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f))
        xpTextHideAnim.startDelay = 1400
        xpTextHideAnim.duration = 400
        xpTextHideAnim.start()
    }
}

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
import com.airbnb.lottie.LottieComposition
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.ui.activities.BoltzPracticeActivity
import com.arsalabangash.boltz.practice.utils.getRandomElement
import com.arsalabangash.boltz.practice.utils.onAnimEnd
import kotlinx.android.synthetic.main.fragment_answer_feedback.view.*


data class AnimationData(val composition: LottieComposition, val speed: Float, val scale: Float)

class AnswerFeedbackFragment : Fragment() {

    private lateinit var boltzPracticeActivity: BoltzPracticeActivity
    private lateinit var correctMP: MediaPlayer
    private lateinit var correct2MP: MediaPlayer
    private lateinit var correct3MP: MediaPlayer
    private lateinit var correct4MP: MediaPlayer
    private lateinit var correct5MP: MediaPlayer
    private lateinit var inCorrectMP: MediaPlayer

    private lateinit var xpView: TextView
    private lateinit var streakView: TextView
    private lateinit var animationView: LottieAnimationView
    private lateinit var correctFeedbackContainer: RelativeLayout
    private lateinit var incorrectFeedbackContainer: FrameLayout

    private lateinit var animations: HashMap<String, AnimationData>
    private val animationNames = arrayOf("clap.json", "correct_check.json", "done.json", "emoji_shock.json")
    private val streakAnimationNames = arrayOf("clap.json", "done.json", "emoji_shock.json")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragmentView = inflater.inflate(R.layout.fragment_answer_feedback, container, false)
        boltzPracticeActivity = activity as BoltzPracticeActivity
        correctMP = MediaPlayer.create(boltzPracticeActivity, R.raw.correct)
        correct2MP = MediaPlayer.create(boltzPracticeActivity, R.raw.correct2)
        correct3MP = MediaPlayer.create(boltzPracticeActivity, R.raw.correct3)
        correct4MP = MediaPlayer.create(boltzPracticeActivity, R.raw.correct4)
        correct5MP = MediaPlayer.create(boltzPracticeActivity, R.raw.correct5)
        inCorrectMP = MediaPlayer.create(boltzPracticeActivity, R.raw.incorrect)
        bindViews(fragmentView)
        animations = hashMapOf(
                "balloons.json" to AnimationData(
                        LottieComposition.Factory.fromFileSync(boltzPracticeActivity,
                                "balloons.json"), 6f, 1f),
                "clap.json" to AnimationData(
                        LottieComposition.Factory.fromFileSync(boltzPracticeActivity,
                                "clap.json"), 0.8f, 0.65f),
                "correct_check.json" to AnimationData(
                        LottieComposition.Factory.fromFileSync(boltzPracticeActivity,
                                "correct_check.json"), 1f, 0.5f),
                "done.json" to AnimationData(
                        LottieComposition.Factory.fromFileSync(boltzPracticeActivity,
                                "done.json"), 2f, 0.5f),
                "emoji_shock.json" to AnimationData(
                        LottieComposition.Factory.fromFileSync(boltzPracticeActivity,
                                "emoji_shock.json"), 2.5f, 2.5f)
        )
        return fragmentView
    }

    fun bindViews(fragmentView: View) {
        xpView = fragmentView.feedback_xp
        animationView = fragmentView.feedback_correct_anim
        correctFeedbackContainer = fragmentView.feedback_correct
        incorrectFeedbackContainer = fragmentView.feedback_incorrect
        streakView = fragmentView.feedback_streak
    }

    fun correctAnswer(xp: Long, streak: Int) {
        animateXP(xp)
        var animation = "correct_check.json"
        if (streak <= 1) {
            playSound(correctMP)
        } else if (streak == 2) {
            playSound(correct2MP)
        } else if (streak == 3) {
            playSound(correct3MP)
            animation = streakAnimationNames.getRandomElement()
            animateStreak(streak)
        } else if (streak == 4) {
            playSound(correct4MP)
        } else if (streak % 5 == 0) {
            playSound(correct5MP)
            animation = streakAnimationNames.getRandomElement()
            animateStreak(streak)
        } else {
            playSound(correct5MP)
            animation = animationNames.getRandomElement()
            animateStreak(streak)
        }
        correctFeedbackContainer.alpha = 1f
        playAnimation(animation)
    }

    private fun playSound(sound: MediaPlayer) {
        if (boltzPracticeActivity.getPracticeOptions().isVolumeOn) {
            sound.start()
        }
    }

    fun incorrectAnswer() {
        if (boltzPracticeActivity.getPracticeOptions().isVolumeOn) {
            inCorrectMP.start()
        }
        incorrectFeedbackContainer.alpha = 1f
        incorrectFeedbackContainer.animate().alpha(0f).duration = 800
    }

    private fun playAnimation(animation: String) {
        val animationData = animations[animation]!!
        animationView.progress = 0f
        animationView.alpha = 1f
        animationView.setSpeed(animationData.speed)
        animationView.scale = animationData.scale
        animationView.setComposition(animationData.composition)
        animationView.playAnimation()
        animationView.onAnimEnd {
            correctFeedbackContainer.animate().alpha(0f)
            animationView.animate().alpha(0f)
            boltzPracticeActivity.answerFeedbackFinished()

        }
    }

    private fun animateStreak(streak: Int) {
        val streakString = StringBuilder()
        streakString.append(streak)
        streakString.append(" in a row!")
        animateTextView(streakString.toString(), streakView)
    }

    private fun animateXP(xp: Long) {
        val xpString = StringBuilder("+")
        xpString.append(xp)
        xpString.append(" xp")
        animateTextView(xpString.toString(), xpView)
    }

    private fun animateTextView(text: String, textView: TextView) {
        textView.text = text
        textView.animate().alpha(1f).setDuration(100)
        textView.animate().scaleX(2.5f).setDuration(300)
        textView.animate().scaleY(2.5f).setDuration(300)
        val textHideAnim = ObjectAnimator.ofPropertyValuesHolder(textView,
                PropertyValuesHolder.ofFloat(View.ALPHA, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f))
        textHideAnim.startDelay = 1400
        textHideAnim.duration = 400
        textHideAnim.start()
    }
}
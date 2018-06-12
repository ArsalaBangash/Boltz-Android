package com.arsalabangash.boltz.practice.ui.fragments

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.models.PracticeOptions
import com.arsalabangash.boltz.practice.ui.activities.BoltzPracticeActivity
import com.arsalabangash.boltz.practice.ui.adapters.ChallengeViewAdapter
import com.arsalabangash.boltz.practice.ui.controllers.PracticeController
import com.arsalabangash.boltz.practice.ui.views.ChallengeTextInput
import com.arsalabangash.boltz.practice.utils.animateInfinite
import com.arsalabangash.boltz.practice.utils.onAnimEnd
import com.arsalabangash.boltz.practice.utils.onAnimStart
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import io.reactivex.rxkotlin.subscribeBy
import katex.hourglass.`in`.mathlib.MathView
import kotlinx.android.synthetic.main.fragment_practice.view.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class PracticeFragment : Fragment() {


    private lateinit var xpCountdownBar: ProgressBar
    private lateinit var xpLeftText: TextView
    private lateinit var sessionProgressbar: ProgressBar

    private lateinit var challengeViewAdapter: ChallengeViewAdapter
    private lateinit var challengeViewHolder: AdapterViewAnimator

    private lateinit var practiceContent: RelativeLayout
    private lateinit var readyStateProgress: ProgressBar
    private lateinit var mathContentFadeIn: ObjectAnimator
    private lateinit var checkButton: Button
    private var sessionReady: Boolean = false
    private var sessionPaused = false
    internal lateinit var controller: PracticeController
    private lateinit var readyStateAnimation: ObjectAnimator
    private lateinit var inputView: ChallengeTextInput

    lateinit var boltzPracticeActivity: BoltzPracticeActivity
    private lateinit var practiceView: View
    private lateinit var practiceOptions: PracticeOptions


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragmentView = inflater.inflate(R.layout.fragment_practice, container, false)
        boltzPracticeActivity = activity as BoltzPracticeActivity
        practiceOptions = boltzPracticeActivity.getPracticeOptions()
        controller = PracticeController(this, practiceOptions.practiceChallenges, practiceOptions.level)

        bindViews(fragmentView)
        mathContentFadeIn = ObjectAnimator.ofFloat(practiceContent, "alpha", 1f)
        mathContentFadeIn.duration = 1000
        mathContentFadeIn.startDelay = 600
        practiceView = fragmentView
        controller.readyCheck().subscribeBy(
                onNext = {
                    challengeViewAdapter.notifyDataSetChanged()
                    startPractice(practiceOptions.showPracticeTutorial)

                },
                onError = {}
        )

        return fragmentView
    }

    private fun bindViews(fragmentView: View) {
        practiceContent = fragmentView.findViewById(R.id.session_ready_content)
        xpLeftText = fragmentView.findViewById(R.id.practice_xp_left)
        readyStateProgress = fragmentView.session_ready_progress
        readyStateProgress.max = 100000

        readyStateAnimation = animateInfinite(readyStateProgress, "progress")

        challengeViewAdapter = ChallengeViewAdapter(this, controller.challenges)
        challengeViewHolder = fragmentView.findViewById(R.id.session_challenge_holder)
        challengeViewHolder.setInAnimation(boltzPracticeActivity, R.animator.slide_from_right)
        challengeViewHolder.setOutAnimation(boltzPracticeActivity, R.animator.slide_to_left)
        challengeViewHolder.adapter = challengeViewAdapter

        xpCountdownBar = fragmentView.findViewById(R.id.practice_xp_countdown)
        sessionProgressbar = fragmentView.findViewById(R.id.practice_progress)
        sessionProgressbar.progress = 0
        sessionProgressbar.max = practiceOptions.questionCount
    }

    private fun startPractice(showTutorial: Boolean) {
        readyStateAnimation.cancel()
        readyStateProgress.animate().alpha(0f)

        challengeViewHolder.showNext()
        if (showTutorial) {
            mathContentFadeIn.start()
            showTutorialDialog()
        } else {
            mathContentFadeIn.onAnimStart {
                configureCountDownTimer()
                sessionReady = true
            }
            mathContentFadeIn.start()
            controller.setStartTime()
        }
    }

    private fun startTutorial() {
        val questionView = practiceView.findViewById<MathView>(R.id.questionView)
        val answerView = practiceView.findViewById<FrameLayout>(R.id.inputContainer)
        checkButton = practiceView.findViewById<Button>(R.id.checkButton)
        val tutorialSequence = TapTargetSequence(boltzPracticeActivity)
        tutorialSequence.targets(
                TapTarget.forView(sessionProgressbar,
                        resources.getString(R.string.session_tutorial_welcome_title),
                        resources.getString(R.string.session_tutorial_welcome_message))
                        .cancelable(false)
                        .outerCircleColor(R.color.colorSecondary)
                        .textColor(R.color.colorWhite)
                        .transparentTarget(true)
                        .tintTarget(false)
                        .descriptionTextColor(R.color.colorWhite)
                        .targetRadius(120),
                TapTarget.forView(answerView,
                        resources.getString(R.string.session_tutorial_input_title),
                        resources.getString(R.string.session_tutorial_input_message))
                        .cancelable(false)
                        .outerCircleColor(R.color.colorSecondary)
                        .textColor(R.color.colorWhite)
                        .transparentTarget(true)
                        .tintTarget(false)
                        .descriptionTextColor(R.color.colorWhite)
                        .targetRadius(200),
                TapTarget.forView(checkButton,
                        resources.getString(R.string.session_tutorial_check_title),
                        resources.getString(R.string.session_tutorial_check_message))
                        .cancelable(false)
                        .outerCircleColor(R.color.colorSecondary)
                        .textColor(R.color.colorWhite)
                        .transparentTarget(true)
                        .tintTarget(false)
                        .descriptionTextColor(R.color.colorWhite)
                        .targetRadius(75),
                TapTarget.forView(sessionProgressbar,
                        resources.getString(R.string.session_tutorial_bars_title),
                        resources.getString(R.string.session_tutorial_bars_message))
                        .cancelable(false)
                        .descriptionTextColor(R.color.colorWhite)
                        .outerCircleColor(R.color.colorSecondary)
                        .textColor(R.color.colorWhite)
                        .transparentTarget(true)
                        .tintTarget(false)
                        .targetRadius(120),
                TapTarget.forView(questionView,
                        resources.getString(R.string.session_tutorial_question_title),
                        resources.getString(R.string.session_tutorial_question_message))
                        .cancelable(false)
                        .outerCircleColor(R.color.colorSecondary)
                        .textColor(R.color.colorWhite)
                        .transparentTarget(true)
                        .tintTarget(false)
                        .descriptionTextColor(R.color.colorWhite)
                        .targetRadius(120)
        ).listener(
                object : TapTargetSequence.Listener {
                    override fun onSequenceCanceled(lastTarget: TapTarget?) {

                    }

                    override fun onSequenceFinish() {
                        configureCountDownTimer()
                        sessionReady = true
                        controller.setStartTime()
                        readyStateProgress.visibility = View.GONE
                    }

                    override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {
                    }

                })
        tutorialSequence.start()
    }

    private fun showTutorialDialog() {
        AlertDialog.Builder(boltzPracticeActivity)
                .setTitle("Welcome to your Math Practice Session!")
                .setMessage("Would you like to take a short tour to see how everything works?")
                .setPositiveButton("Sure", { _, _ ->
                    startTutorial()
                })
                .setNegativeButton("No Thanks", { _, _ ->
                    configureCountDownTimer()
                    sessionReady = true
                    controller.setStartTime()
                    readyStateProgress.visibility = View.GONE
                })
                .create()
                .show()
    }

    private fun setCountDownProgress(time: Int) {
        xpCountdownBar.progress = time
    }

    private fun configureCountDownTimer() {
        this.controller.configXPTimer(xpCountdownBar, xpLeftText)
    }

    fun checkAnswer() {
        this.controller.checkAnswer()
    }

    fun correctAnswer() {
        sessionProgressbar.progress++
        if (sessionProgressbar.progress == practiceOptions.questionCount) {
            practiceContent.animate().alpha(0f)
            endSession(didComplete = true)
        } else proceedNextQuestion(true)
    }

    private fun endSession(didComplete: Boolean) {
        practiceContent.animate().alpha(0f).duration = 2000
        this.controller.endSession(didComplete).subscribeBy(
                onNext = {
                    boltzPracticeActivity.endSession(it)
                }
        )
    }

    fun incorrectAnswer() {
        proceedNextQuestion(false)
    }

    private fun proceedNextQuestion(wasPrevCorrect: Boolean) {
        challengeViewAdapter.notifyDataSetChanged()
        challengeViewHolder.showNext()
        if (wasPrevCorrect) {
            practiceContent.alpha = 0f
        }
    }

    fun answerFeedbackFinished() {
        controller.setStartTime()
        setCountDownProgress(controller.resetCountdown())
        practiceContent.animate().alpha(1f)
    }

    fun inputButtonPressed(view: View) {
        inputView.insertText(view.tag.toString())
    }

    fun backspacePressed() {
        inputView.deleteText()
    }

    fun setInput(view: View) {
        inputView = view as ChallengeTextInput
    }

    fun stopPractice() {
        sessionPaused = true
        if (sessionReady) {
            this.controller.pauseXP()
        }
    }

    fun resumePractice() {
        if (sessionPaused) {
            this.controller.resumeXP(xpCountdownBar, xpLeftText)
        }
        sessionPaused = false
    }

}

package com.arsalabangash.boltz.practice.ui.controllers

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import com.arsalabangash.boltz.practice.BoltzPracticeApp
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.challenge.*
import com.arsalabangash.boltz.practice.models.ChallengeModel
import com.arsalabangash.boltz.practice.ui.activities.BoltzPracticeActivity
import com.arsalabangash.boltz.practice.ui.fragments.PracticeFragment
import com.arsalabangash.boltz.practice.ui.views.ChallengeTextInput
import com.arsalabangash.boltz.practice.ui.views.MultipleChoiceGridView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class PracticeController(val context: PracticeFragment, challengeNames: ArrayList<String>,
                         val level: String, val boltzPracticeActivity: BoltzPracticeActivity) {

    val challenges = ChallengeQueue()
    private var inputView: ChallengeTextInput? = null
    var sessionEndMP = MediaPlayer.create(context.activity, R.raw.session_end)
    @Inject
    lateinit var challengeUtils: ChallengeUtils
    private val challengeGenerator: ChallengeGenerator
    var userAnswers = ArrayList<View>()
    private lateinit var countDown: CountDownTimer
    private var challengeStartTime: Long = 0
    private var millisLeft: Long = 0
    private val challengeList = arrayListOf<ChallengeModel>()
    private var totalXP: Long = 0
    private var totalTime: Long = 0
    private var BASE_XP: Int = 0
    private var TIME_XP: Int = 0

    private var correctCount: Int = 0
    private var attemptCount: Int = 0
    private var conceptCount: Int

    init {
        BoltzPracticeApp.component.inject(this)
        challengeGenerator = ChallengeGenerator(level, challengeNames)
        conceptCount = challengeNames.size
        setDifficultyXP()

    }

    fun setDifficultyXP() {
        when (level) {
            "Basic" -> {
                BASE_XP = 250
                TIME_XP = 50
            }
            "Normal" -> {
                BASE_XP = 500
                TIME_XP = 35
            }
            "Advanced" -> {
                BASE_XP = 750
                TIME_XP = 25
            }
            else -> {
                BASE_XP = 500
                TIME_XP = 35
            }
        }
    }

    fun handleCorrect() {
        with(challenges) {
            dequeHeadChallenge()
            if (numIncorrect > 0) {
                numIncorrect--
                incorrectInRow = 0
            } else {
                addQuestion()
            }
        }
    }

    fun getCurrentQuestion() = challenges.getHeadChallenge()

    fun addQuestion() {
        challenges.addChallenge(challengeGenerator.generateChallenge())
    }

    fun handleIncorrect() {
        with(challenges) {
            if (numIncorrect > incorrectInRow) {
                addChallenge(dequeHeadChallenge())
                incorrectInRow++
            } else {
                addChallenge(dequeHeadChallenge())
                incorrectInRow = 0
                addQuestion()
                numIncorrect++
            }
        }
    }

    fun xpTimerInit(timeInMillis: Long, progressBar: ProgressBar, xpLeftText: TextView): CountDownTimer {
        return object : CountDownTimer(timeInMillis, 10) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress = millisUntilFinished.toInt()
                xpLeftText.text = "XP: ${(millisUntilFinished / TIME_XP) + BASE_XP}"
                millisLeft = millisUntilFinished
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                xpLeftText.text = "XP: $BASE_XP"
            }
        }
    }


    fun configXPTimer(progressBar: ProgressBar, xpLeftText: TextView) {
        progressBar.max = getCurrentQuestion().time * 1000
        countDown = xpTimerInit((getCurrentQuestion().time * 1000).toLong(), progressBar, xpLeftText).start()
    }

    fun pauseXP() {
        countDown.cancel()
    }

    fun resumeXP(progressBar: ProgressBar, xpLeftText: TextView) {
        countDown = xpTimerInit(millisLeft, progressBar, xpLeftText).start()
    }

    fun resetCountdown(): Int {
        countDown.cancel()
        countDown.start()
        return getCurrentQuestion().time * 1000
    }

    fun checkAnswer() {
        attemptCount++
        getCurrentQuestion().addAttempt()
        addChallengeTime()
        challengeUtils.checkAnswer(getUserAnswer(), getCurrentQuestion().infixRepr!!, getCurrentQuestion().checkStrategy).subscribeBy(
                onSuccess = {
                    if (it) {
                        val xp = (millisLeft / TIME_XP) + BASE_XP
                        getCurrentQuestion().markSolved()
                        getCurrentQuestion().setXP(xp)
                        challengeList.add(getCurrentQuestion().model)
                        totalXP += xp
                        handleCorrect()
                        correctCount++
                        this.boltzPracticeActivity.correctAnswer(xp)
                    } else {
                        this.boltzPracticeActivity.incorrectAnswer()
                        handleIncorrect()
                    }
                },
                onError = {
                    this.boltzPracticeActivity.incorrectAnswer()
                    handleIncorrect()
                }
        )
    }

    private fun getUserAnswer(): String {
        val currentQuestion = getCurrentQuestion()
        when {
            currentQuestion is FactorizationChallenge -> {
                return "(${(userAnswers[0] as EditText).text})*(${(userAnswers[1] as EditText).text})"
            }
            currentQuestion is DegreeTrigChallenge || currentQuestion is RadianTrigChallenge -> {
                val multipleChoiceGrid = userAnswers[0] as MultipleChoiceGridView
                val checkedButtonID = multipleChoiceGrid.checkedRadioButtonId
                if (checkedButtonID != -1) {
                    return multipleChoiceGrid.findViewById<RadioButton>(checkedButtonID).tag.toString()
                } else return ""
            }
            else -> return (userAnswers[0] as EditText).text.toString()
        }
    }

    /**
     * Appends [text] to the current [inputView]
     */
    fun insertToAnswer(text: String) {
        this.inputView?.insertText(text)
    }

    /**
     * Sets the current [inputView] to specified [ChallengeTextInput] view
     */
    fun setInputView(challengeTextInput: ChallengeTextInput) {
        this.inputView = challengeTextInput
    }

    /**
     * Deletes text from the current [inputView]
     */
    fun deleteAnswer() {
        this.inputView?.deleteText()
    }

    /**
     * Defines an observable that waits for 2 seconds and then evaluates an expression using the
     * [ExprEvaluator] class to ensure that the math engine is loaded into runtime memory.
     *
     * Once loaded, two questions are added to the [challenges] queue and onComplete() is triggered
     * so that the subscriber is notified that the session is ready
     */
    fun readyCheck(): Observable<Any> {
        return Observable.create<Any> { sub ->
            SystemClock.sleep(2000)
            challengeUtils.evaluate("1")
            this.addQuestion()
            this.addQuestion()
            sub.onNext(0)
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun setStartTime() {
        this.challengeStartTime = System.currentTimeMillis()
    }

    fun addChallengeTime() {
        val timeTaken = System.currentTimeMillis() - challengeStartTime
        getCurrentQuestion().addTimeTaken(timeTaken)
        totalTime += timeTaken
    }

    /**
     * At the end of the session, the user's XP is updated and a [SessionModel] is
     * created and added to the database as well. An observable is sent to the context, notifying
     * the context of the state of the database transactions
     */
    fun endSession(didComplete: Boolean): Observable<Unit> {
        if (didComplete) {
            sessionEndMP.start()
        }
        return Observable.fromCallable {
            if (didComplete) {
                SystemClock.sleep(2000)
            }
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
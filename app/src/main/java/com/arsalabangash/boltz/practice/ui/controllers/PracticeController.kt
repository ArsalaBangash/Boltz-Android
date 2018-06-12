package com.arsalabangash.boltz.practice.ui.controllers

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import com.arsalabangash.boltz.practice.challenge.*
import com.arsalabangash.boltz.practice.models.ChallengeModel
import com.arsalabangash.boltz.practice.models.PracticeData
import com.arsalabangash.boltz.practice.ui.fragments.PracticeFragment
import com.arsalabangash.boltz.practice.ui.views.MultipleChoiceGridView
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class PracticeController(val context: PracticeFragment, challengeNames: ArrayList<String>,
                         val level: String) {

    val challenges = ChallengeQueue()
    private val challengeUtils = ChallengeUtils.getInstance(context.context!!)
    private val challengeGenerator = ChallengeGenerator(challengeUtils, level, challengeNames)
    var userAnswers = ArrayList<View>()
    private lateinit var xpCountDown: CountDownTimer
    private var challengeStartTime: Long = 0
    private var millisLeft: Long = 0
    private val challengeModels = arrayListOf<ChallengeModel>()
    private var totalXP: Long = 0
    private var totalTime: Long = 0
    private val xpData: XPData = setXP(level)

    private var correctCount: Int = 0
    private var attemptCount: Int = 0

    /**
     * If there were no incorrectly answered challenges, we add a new challenge to the
     * [ChallengeQueue]. Otherwise, we show an older incorrectly answered challenge.
     */
    private fun handleCorrectAnswer() {
        with(challenges) {
            dequeHeadChallenge()
            correctInRow++
            if (numIncorrect > 0) {
                numIncorrect--
                incorrectInRow = 0
            } else {
                addQuestion()
            }
        }
    }

    /**
     * The [ChallengeQueue] is configured so that users have to try again at answering a question
     * that they got incorrect.
     *
     * However, if they continue to answer a group of questions incorrectly, we add a new challenge
     * to freshen things up.
     */
    private fun handleIncorrectAnswer() {
        with(challenges) {
            correctInRow = 0
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


    private fun getCurrentQuestion() = challenges.getHeadChallenge()

    private fun addQuestion() {
        challenges.addChallenge(challengeGenerator.generateChallenge())
    }

    /**
     * Configures a countdown timer for a new challenge, updating the [progressBar] and [xpLeftText]
     * on each tick
     */
    fun configXPTimer(progressBar: ProgressBar, xpLeftText: TextView) {
        val challengeTime = getCurrentQuestion().time * 1000
        progressBar.max = challengeTime
        xpCountDown = configureXPViews(challengeTime.toLong(), progressBar, xpLeftText).start()
    }

    fun pauseXP() {
        xpCountDown.cancel()
    }

    private fun configureXPViews(challengeTime: Long, progressBar: ProgressBar, xpLeftText: TextView): CountDownTimer {
        return object : CountDownTimer(challengeTime, 10) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress = millisUntilFinished.toInt()
                xpLeftText.text = "XP: ${(millisUntilFinished / xpData.timeFactor) + xpData.baseXP}"
                millisLeft = millisUntilFinished
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                xpLeftText.text = "XP: ${xpData.baseXP}"
            }
        }
    }

    fun resumeXP(progressBar: ProgressBar, xpLeftText: TextView) {
        xpCountDown = configureXPViews(millisLeft, progressBar, xpLeftText).start()
    }

    fun resetCountdown(): Int {
        xpCountDown.cancel()
        xpCountDown.start()
        return getCurrentQuestion().time * 1000
    }

    fun checkAnswer() {
        attemptCount++
        getCurrentQuestion().addAttempt()
        addChallengeTime()
        challengeUtils.checkAnswer(getUserAnswer(), getCurrentQuestion().infixRepr!!,
                getCurrentQuestion().checkStrategy).subscribe(onAnswerCheckedSubcriber)
    }

    private val onAnswerCheckedSubcriber = object : SingleObserver<Boolean> {
        override fun onSubscribe(d: Disposable) {}

        override fun onSuccess(correct: Boolean) {
            if (correct) {
                val xp = (millisLeft / xpData.timeFactor) + xpData.baseXP
                getCurrentQuestion().markSolved()
                getCurrentQuestion().setXP(xp)
                challengeModels.add(getCurrentQuestion().model)
                totalXP += xp
                handleCorrectAnswer()
                correctCount++
                context.boltzPracticeActivity.correctAnswer(xp, challenges.correctInRow)
            } else {
                context.boltzPracticeActivity.incorrectAnswer()
                handleIncorrectAnswer()
            }
        }

        override fun onError(e: Throwable) {
            context.boltzPracticeActivity.incorrectAnswer()
            handleIncorrectAnswer()
        }
    }

    /**
     * We have to adjust the way we get the user's answer based on the current type of challenge
     */
    private fun getUserAnswer(): String {
        val currentQuestion = getCurrentQuestion()
        return when (currentQuestion) {
            is FactorizationChallenge -> {
                "(${(userAnswers[0] as EditText).text})*(${(userAnswers[1] as EditText).text})"
            }
            is TrigChallenge -> {
                val multipleChoiceGrid = userAnswers[0] as MultipleChoiceGridView
                val checkedButtonID = multipleChoiceGrid.checkedRadioButtonId
                if (checkedButtonID != -1) {
                    multipleChoiceGrid.findViewById<RadioButton>(checkedButtonID).tag.toString()
                } else ""
            }
            else -> (userAnswers[0] as EditText).text.toString()
        }
    }

    /**
     * Defines an observable that waits for 2 seconds and then evaluates an expression using the
     * [ExprEvaluator] class to ensure that the math engine is loaded into runtime memory.
     *
     * Once loaded, two questions are added to the [challenges] queue and onComplete() is triggered
     * so that the subscriber is notified that the session is ready
     */
    fun readyCheck(): Observable<Any> {
        return Observable.create<Any> { sub: ObservableEmitter<Any> ->
            SystemClock.sleep(2000)
            challengeUtils.evaluate("1")
            addQuestion()
            addQuestion()
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

    fun getPracticeData(didComplete: Boolean): PracticeData {
        return PracticeData(
                challengeModels = challengeModels,
                correctCount = correctCount,
                attemptCount = attemptCount,
                totalXP = totalXP,
                totalTime = totalTime,
                didComplete = didComplete)
    }

    /**
     * At the end of the practicd session, certaain data has to be sent to the backend. Thus,
     * an observable is sent to the context, giving a 2 second delay for UX purposes and then
     * emitting an event once all backend operations have been completed
     */
    fun endSession(didComplete: Boolean): Observable<PracticeData> {
        return Observable.fromCallable {
            if (didComplete) {
                SystemClock.sleep(2000)
            }
            getPracticeData(didComplete)
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
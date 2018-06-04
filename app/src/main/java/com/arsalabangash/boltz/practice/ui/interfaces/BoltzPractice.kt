package com.arsalabangash.boltz.practice.ui.interfaces

import android.view.View
import com.arsalabangash.boltz.practice.models.PracticeData
import com.arsalabangash.boltz.practice.ui.fragments.AnswerFeedbackFragment
import com.arsalabangash.boltz.practice.ui.fragments.PracticeFragment

/**
 * Defines methods needed by the Practice activity.
 * Important because certain functionality needs to be implemented in the actual app.
 */
interface BoltzPractice {
    val practiceFragment: PracticeFragment
    val answerFeedbackFragment: AnswerFeedbackFragment

    fun onInputButtonPressed(view: View) {
        practiceFragment.inputButtonPressed(view)
    }

    fun onBackspacePressed(view: View) {
        practiceFragment.backspacePressed()
    }

    fun setInput(view: View) {
        practiceFragment.setInput(view)
    }

    fun check(view: View) {
        practiceFragment.checkAnswer()
    }

    fun correctAnswer(xp: Long, streak: Int) {
        practiceFragment.correctAnswer()
        answerFeedbackFragment.correctAnswer(xp, streak)
    }

    fun incorrectAnswer() {
        practiceFragment.incorrectAnswer()
        answerFeedbackFragment.incorrectAnswer()
    }

    fun answerFeedbackFinished() {
        practiceFragment.answerFeedbackFinished()
    }

    fun endSession(practiceData: PracticeData)
}
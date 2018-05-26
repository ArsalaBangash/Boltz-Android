package com.arsalabangash.boltz.practice.ui.interfaces

import android.view.View
import com.arsalabangash.boltz.practice.ui.fragments.AnswerFeedbackFragment
import com.arsalabangash.boltz.practice.ui.fragments.PracticeFragment

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

    fun correctAnswer(xp: Long) {
        practiceFragment.correctAnswer()
        answerFeedbackFragment.correctAnswer(xp)
    }

    fun incorrectAnswer() {
        practiceFragment.incorrectAnswer()
        answerFeedbackFragment.incorrectAnswer()
    }

    fun answerFeedbackFinished() {
        practiceFragment.answerFeedbackFinished()
    }
}
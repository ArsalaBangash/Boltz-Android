package com.arsalabangash.boltz.practice.ui.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.challenge.Challenge
import com.arsalabangash.boltz.practice.challenge.DegreeTrigChallenge
import com.arsalabangash.boltz.practice.challenge.RadianTrigChallenge
import katex.hourglass.`in`.mathlib.MathView
import kotlinx.android.synthetic.main.input_multiple_choice.view.*
import java.util.*

/**
 * This class holds the UI representation of a particular challenge. The [challenge] parameter
 * specified in the constructor refers to the current challenge in decimal, so that we may use its
 * layout ID to set up the UI and it's decimal to display to the user.
 */
class ChallengeView(context: Context, challenge: Challenge) {

    private var challengeView: View = LayoutInflater.from(context).inflate(challenge.layoutID!!, null)

    init {
        val questionView: MathView = challengeView.findViewById<MathView>(R.id.questionView)
        questionView.setDisplayText(challenge.latexRepr)
        questionView.setTextSize(24)
        val label: TextView = challengeView.findViewById(R.id.challenge_label)
        label.text = challenge.label

        if (challenge is DegreeTrigChallenge) {
            setMultipleChoices(challenge.multipleChoices)
        }

        if (challenge is RadianTrigChallenge) {
            setMultipleChoices(challenge.multipleChoices)
        }
    }

    private fun setMultipleChoices(choices: ArrayList<String>) {
        Collections.shuffle(choices)
        challengeView.optionA.text = choices[0]
        challengeView.optionA.tag = choices[0]
        challengeView.optionB.text = choices[1]
        challengeView.optionB.tag = choices[1]
        challengeView.optionC.text = choices[2]
        challengeView.optionC.tag = choices[2]
        challengeView.optionD.text = choices[3]
        challengeView.optionD.tag = choices[3]
    }

    fun getChallengeView(): View = challengeView

}
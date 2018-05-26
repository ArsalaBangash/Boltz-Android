package com.arsalabangash.boltz.practice.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.arsalabangash.boltz.practice.challenge.ChallengeQueue
import com.arsalabangash.boltz.practice.challenge.DegreeTrigChallenge
import com.arsalabangash.boltz.practice.challenge.FactorizationChallenge
import com.arsalabangash.boltz.practice.challenge.RadianTrigChallenge
import com.arsalabangash.boltz.practice.ui.fragments.PracticeFragment
import com.arsalabangash.boltz.practice.ui.views.ChallengeTextInput
import com.arsalabangash.boltz.practice.ui.views.ChallengeView

import kotlinx.android.synthetic.main.input_factorization.view.*
import kotlinx.android.synthetic.main.input_multiple_choice.view.*

/**
 * This adapter observes the [ChallengeQueue] from the current Practice session and populates the
 * correct [ChallengeView] for the current challenge at the head of the queue
 */
class ChallengesViewAdapter(val appContext: PracticeFragment, val challenges: ChallengeQueue) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val currentChallenge = challenges.getHeadChallenge()
        val challengeView = ChallengeView(appContext.activity!!, currentChallenge).getChallengeView()

        /* If pulling an answer for a challenge is more complex than reading from a single view,
         * then set [useranswers] accordingly
         */
        when (currentChallenge) {
            is FactorizationChallenge -> appContext.controller.userAnswers = arrayListOf(challengeView.answerView,
                    challengeView.answerView2)
            is RadianTrigChallenge -> {
                appContext.controller.userAnswers = arrayListOf(challengeView.multiple_choice_options)
            }
            is DegreeTrigChallenge -> {
                appContext.controller.userAnswers = arrayListOf(challengeView.multiple_choice_options)
            }
            else -> appContext.controller.userAnswers = arrayListOf(challengeView.answerView)
        }

        // Focus on the Text Input view if there is one
        if (challengeView.answerView != null) {
            challengeView.answerView.requestFocus()
            appContext.controller.setInputView(challengeView.answerView as ChallengeTextInput)
        }
        return challengeView
    }

    override fun getItem(position: Int): Any {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return challenges.size
    }

}
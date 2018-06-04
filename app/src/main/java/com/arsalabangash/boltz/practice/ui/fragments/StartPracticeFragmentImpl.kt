package com.arsalabangash.boltz.practice.ui.fragments

import android.content.Intent
import android.widget.Toast
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.ui.activities.PracticeActivity

/**
 * Implementation of [StartPracticeFragment] that defines which activity should be
 * started for practice
 */
class StartPracticeFragmentImpl : StartPracticeFragment() {

    override fun startPractice() {
        val practiceIntent = Intent(context, PracticeActivity::class.java)
        setIntentData(practiceIntent)
        if (practiceChallenges.isEmpty()) {
            Toast.makeText(activity, getString(R.string.practice_challenges_empty), Toast.LENGTH_SHORT).show()
            return
        }
        startActivity(practiceIntent, android.app.ActivityOptions.makeCustomAnimation(activity,
                R.anim.slide_from_right, R.anim.slide_to_left).toBundle())
        activity!!.finish()
    }

    companion object {
        fun newInstance(): StartPracticeFragment {
            return StartPracticeFragmentImpl()
        }
    }

}
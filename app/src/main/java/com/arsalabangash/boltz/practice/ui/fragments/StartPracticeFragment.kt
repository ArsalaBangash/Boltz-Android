package com.arsalabangash.boltz.practice.ui.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arsalabangash.boltz.practice.R
import kotlinx.android.synthetic.main.fragment_startpractice.view.*

/**
 * A fragment that defines behaviour for launching a practice session.
 *
 * Made abstract because official app calls on backend to set some Intent Data
 */
abstract class StartPracticeFragment : Fragment() {

    protected lateinit var sharedPref: SharedPreferences
    protected lateinit var challengeNames: Array<String>
    protected val practiceChallenges = arrayListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragmentView = inflater.inflate(R.layout.fragment_startpractice, container, false)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)

        challengeNames = resources.getStringArray(R.array.challenge_types)
        fragmentView.start_practice.setOnClickListener {
            startPractice()
        }
        return fragmentView
    }

    protected fun setIntentData(practiceIntent: Intent) {
        //Practice Tutorial
        if (sharedPref.getBoolean(getString(R.string.show_practice_tutorial), false)) {
            practiceIntent.putExtra(getString(R.string.show_practice_tutorial), true)
            sharedPref.edit().putBoolean(getString(R.string.show_practice_tutorial), false).apply()
        }

        practiceIntent.putExtra(getString(R.string.question_count), sharedPref.getInt(getString(R.string.question_count), 8))
        practiceIntent.putExtra(getString(R.string.practice_level), sharedPref.getString(getString(R.string.difficulty), "Normal"))
        practiceIntent.putExtra(getString(R.string.is_premium_user), false)

        //Practice Challenges
        for (challengeName in challengeNames) {
            if (sharedPref.getBoolean(challengeName, false)) {
                practiceChallenges.add(challengeName)
            }
        }
        practiceIntent.putStringArrayListExtra("practiceChallenges", practiceChallenges)
    }

    /**
     * Starts the math practice session with the time and difficulty
     * Made abstract to allow for official app to start its own implementation of the
     * BoltzPracticeActivity
     */
    abstract fun startPractice()
}
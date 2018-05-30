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


abstract class StartPracticeFragment : Fragment() {

    protected lateinit var sharedPref: SharedPreferences
    protected lateinit var challengeNames: Array<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragmentView = inflater.inflate(R.layout.fragment_startpractice, container, false)
        challengeNames = resources.getStringArray(R.array.challenge_types)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        bindViews(fragmentView)
        return fragmentView
    }


    fun bindViews(fragmentView: View) {
        fragmentView.start_practice.setOnClickListener {
            startPractice()
        }
    }

    protected fun setIntentData(practiceIntent: Intent) {
        if (sharedPref.getBoolean(getString(R.string.show_practice_tutorial), false)) {
            practiceIntent.putExtra(getString(R.string.show_practice_tutorial), true)
            sharedPref.edit().putBoolean(getString(R.string.show_practice_tutorial), false).apply()
        }
        practiceIntent.putExtra(getString(R.string.question_count), sharedPref.getInt(getString(R.string.question_count), 8))
        practiceIntent.putExtra(getString(R.string.practice_level), sharedPref.getString(getString(R.string.difficulty), "Normal"))
        practiceIntent.putExtra(getString(R.string.is_premium_user), false)
        val sessionChallenges = arrayListOf<String>()
        challengeNames.forEach {
            if (sharedPref.getBoolean(it, false)) {
                sessionChallenges.add(it)
            }
        }
        if (sessionChallenges.isEmpty()) {
            Toast.makeText(activity, "Please select at least one Math concept", Toast.LENGTH_SHORT).show()
            return
        }
        practiceIntent.putStringArrayListExtra("practiceChallenges", sessionChallenges)
    }

    /**
     * Starts the math practice session with the time and difficulty
     */
    abstract fun startPractice()
}
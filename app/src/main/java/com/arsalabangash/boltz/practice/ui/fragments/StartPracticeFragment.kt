package com.arsalabangash.boltz.practice.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.ui.activities.PracticeActivity
import com.arsalabangash.boltz.practice.ui.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_startpractice.view.*


class StartPracticeFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sessionIntent: Intent
    private lateinit var challengeNames: Array<String>


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        sessionIntent = Intent(context, PracticeActivity::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragmentView = inflater.inflate(R.layout.fragment_startpractice, container, false)
        challengeNames = resources.getStringArray(R.array.challenge_types)
        mainActivity = activity as MainActivity
        sharedPref = PreferenceManager.getDefaultSharedPreferences(mainActivity)
        bindViews(fragmentView)
        return fragmentView
    }


    fun bindViews(fragmentView: View) {
        fragmentView.start_practice.setOnClickListener {
            startPractice()
        }
    }

    /**
     * Starts the math practice session with the time and difficulty
     */
    private fun startPractice() {
        if (sharedPref.getBoolean("session_tutorial", false)) {
            sessionIntent.putExtra("session_tutorial", true)
            sharedPref.edit().putBoolean("session_tutorial", false).apply()
        }
        sessionIntent.putExtra("questions", sharedPref.getInt("questionCount", 8))
        Log.d("questionCount", sharedPref.getInt(getString(R.string.question_count), 8).toString())
        sessionIntent.putExtra(getString(R.string.practice_level), sharedPref.getString("difficulty", "Normal"))
        sessionIntent.putExtra("isPremiumUser", false)
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
        sessionIntent.putStringArrayListExtra("practiceChallenges", sessionChallenges)
        startActivity(sessionIntent, android.app.ActivityOptions.makeCustomAnimation(activity,
                R.anim.slide_from_right, R.anim.slide_to_left).toBundle())
        mainActivity.finish()
    }

    companion object {
        fun newInstance(): StartPracticeFragment {
            return StartPracticeFragment()
        }
    }
}
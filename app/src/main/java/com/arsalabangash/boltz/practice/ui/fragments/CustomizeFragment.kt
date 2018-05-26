package com.arsalabangash.boltz.practice.ui.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.res.TypedArray
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.challenge.ChallengeData
import com.arsalabangash.boltz.practice.ui.activities.MainActivity
import com.arsalabangash.boltz.practice.ui.adapters.ChallengeListAdapter
import kotlinx.android.synthetic.main.fragment_customize.view.*
import kotlin.math.max

class CustomizeFragment : Fragment() {


    //View Declarations
    private lateinit var challengeListView: RecyclerView
    private lateinit var challengeNames: Array<String>
    private lateinit var challengeIcons: TypedArray
    private lateinit var mainActivity: MainActivity
    private lateinit var difficultyButton: Button
    private lateinit var questionCountButton: Button

    //Property Declarations
    private lateinit var sharedPref: SharedPreferences
    private val challengeArray = ArrayList<ChallengeData>()
    private val difficulties = arrayOf<String>("Basic", "Normal", "Advanced")
    private var difficultyIndex = 1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragmentView = inflater.inflate(R.layout.fragment_customize, container, false)
        challengeNames = resources.getStringArray(R.array.challenge_types)
        challengeIcons = resources.obtainTypedArray(R.array.challenge_icons)
        mainActivity = activity as MainActivity
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)

        bindViews(fragmentView)
        return fragmentView
    }

    fun bindViews(fragmentView: View) {
        bindDifficulty(fragmentView)
        bindChallengeList(fragmentView)
        bindQuestionCount(fragmentView)
    }


    private fun bindChallengeList(fragmentView: View) {
        //Binds the challenge list Recycler View and populates it with practiceChallenges
        challengeListView = fragmentView.customize_challenge_list
        challengeListView.layoutManager = LinearLayoutManager(mainActivity, LinearLayout.VERTICAL, false)
        challengeNames.indices.mapTo(challengeArray) {
            ChallengeData(challengeIcons.getDrawable(it), challengeNames[it])
        }
        challengeListView.adapter = ChallengeListAdapter(challengeArray)
        challengeIcons.recycle()
    }

    private fun bindDifficulty(fragmentView: View) {
        difficultyButton = fragmentView.customize_difficulty_button
        val difficulty = sharedPref.getString("difficulty", "Normal")
        difficultyIndex = difficulties.indexOf(difficulty)
        difficultyButton.text = "Difficulty: $difficulty"
        difficultyButton.setOnClickListener { toggleDifficulty() }
    }

    private fun bindQuestionCount(fragmentView: View) {
        questionCountButton = fragmentView.customize_questions_button
        val questionCount = sharedPref.getInt("questionCount", 5)
        questionCountButton.text = "Questions: $questionCount"
        questionCountButton.setOnClickListener { incrementQuestionCount() }
    }

    @SuppressLint("ApplySharedPref")
    private fun incrementQuestionCount() {
        val newQuestionCount = max((sharedPref.getInt("questionCount", 5) % 20) + 1, 5)
        sharedPref.edit().putInt("questionCount", newQuestionCount).commit()
        Log.d("questionCount", sharedPref.getInt("questionCount", 8).toString())
        questionCountButton.text = "Questions: $newQuestionCount"
    }

    private fun toggleDifficulty() {
        difficultyIndex = (difficultyIndex + 1) % 3
        val difficulty = difficulties[difficultyIndex]
        difficultyButton.text = "Difficulty: $difficulty"
        sharedPref.edit().putString("difficulty", difficulty).apply()
    }

    companion object {
        fun newInstance(): CustomizeFragment {
            return CustomizeFragment()
        }
    }
}

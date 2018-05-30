package com.arsalabangash.boltz.practice.ui.fragments

import android.content.Intent
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.ui.activities.PracticeActivity

class StartPracticeFragmentImpl : StartPracticeFragment() {

    override fun startPractice() {
        val practiceIntent = Intent(context, PracticeActivity::class.java)
        setIntentData(practiceIntent)
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
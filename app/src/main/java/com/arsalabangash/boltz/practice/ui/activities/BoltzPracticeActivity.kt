package com.arsalabangash.boltz.practice.ui.activities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.models.PracticeData
import com.arsalabangash.boltz.practice.models.PracticeOptions
import com.arsalabangash.boltz.practice.ui.fragments.AnswerFeedbackFragment
import com.arsalabangash.boltz.practice.ui.fragments.PracticeFragment
import com.arsalabangash.boltz.practice.ui.interfaces.BoltzPractice
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BoltzPracticeActivity : AppCompatActivity(), BoltzPractice {

    //Fragments
    override val practiceFragment: PracticeFragment = PracticeFragment()
    override val answerFeedbackFragment: AnswerFeedbackFragment = AnswerFeedbackFragment()

    private lateinit var endPracticeDialog: AlertDialog
    private lateinit var sharedPref: SharedPreferences
    private lateinit var practiceOptions: PracticeOptions

    //Must be protected because they are used in the official app's child classes
    protected lateinit var finishIntent: Intent
    protected lateinit var practiceData: PracticeData

    fun getPracticeOptions() = practiceOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        setContentView(R.layout.activity_boltz_practice)
        practiceOptions = getPracticeOptionsData()
        endPracticeDialog = buildEndPracticeDialog()
        finishIntent = Intent(this, MainActivity::class.java)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_practice, practiceFragment)
                .replace(R.id.fragment_container_answer_feedback, answerFeedbackFragment)
                .commit()
    }

    //Necessary for algebraic 'x' font
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onStop() {
        super.onStop()
        practiceFragment.stopPractice()
    }

    override fun onResume() {
        super.onResume()
        practiceFragment.resumePractice()
    }

    override fun onBackPressed() {
        endPracticeDialog.show()
    }

    /**
     * Returns a Dialog that allows a user to quit their current practice session
     */
    private fun buildEndPracticeDialog(): AlertDialog {
        //Binds button logic related to the end session dialog
        val endSessionDialogBuilder = AlertDialog.Builder(this)
        endSessionDialogBuilder.setNegativeButton(getString(R.string.cancel), { dialog: DialogInterface, which: Int ->
            run {
                endPracticeDialog.hide()
            }
        })
        endSessionDialogBuilder.setPositiveButton(getString(R.string.quit), { dialog: DialogInterface, which: Int ->
            run {
                endSession(this.practiceFragment.controller.getPracticeData(false))
            }
        })
        endSessionDialogBuilder.setTitle(getString(R.string.dialog_end_session_title))
        endSessionDialogBuilder.setMessage(getString(R.string.dialog_end_session_message))
        val endSessionDialog = endSessionDialogBuilder.create()
        endSessionDialog.setCancelable(false)
        return endSessionDialog
    }

    private fun getPracticeOptionsData(): PracticeOptions {
        return PracticeOptions(
                practiceChallenges = this.intent.getStringArrayListExtra(getString(R.string.practice_challenges)),
                isVolumeOn = sharedPref.getBoolean(getString(R.string.is_volume_on), true),
                questionCount = this.intent.getIntExtra(getString(R.string.question_count), 8),
                showPracticeTutorial = this.intent.getBooleanExtra(getString(R.string.show_practice_tutorial), false),
                level = this.intent.getStringExtra(getString(R.string.practice_level)),
                isPremiumUser = this.intent.getBooleanExtra(getString(R.string.is_premium_user), false)

        )
    }

    /**
     * Nothing to do with practiceData or ads, so simply navigate to the MainActivity
     */
    override fun endSession(practiceData: PracticeData) {
        startActivity(finishIntent, android.app.ActivityOptions.makeCustomAnimation(this,
                R.anim.slide_from_right, R.anim.slide_to_left).toBundle())
        finish()
    }
}
package com.arsalabangash.boltz.practice.ui.adapters


import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.challenge.ChallengeData
import kotlinx.android.synthetic.main.challenge_list_item.view.*

/**
 * This adapter populates the Customize Fragment's list with the name and icon for every
 * challenge in the [challengeList]
 */
class ChallengeListAdapter(private val challengeList: ArrayList<ChallengeData>) :
        RecyclerView.Adapter<ChallengeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListAdapter.ViewHolder {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(parent.context)
        val v = LayoutInflater.from(parent.context).inflate(R.layout.challenge_list_item, parent, false)
        return ViewHolder(v, sharedPref)
    }

    override fun onBindViewHolder(holder: ChallengeListAdapter.ViewHolder, position: Int) {
        holder.bindItems(challengeList[position])
    }

    override fun getItemCount(): Int {
        return challengeList.size
    }

    class ViewHolder(itemView: View, private val sharedPref: SharedPreferences) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(challenge: ChallengeData) {
            itemView.challenge_list_check.isChecked = sharedPref.getBoolean(challenge.name, false)
            itemView.challenge_list_check.setOnClickListener {
                toggleChallengeState(challenge.name)
            }
            itemView.setOnClickListener {
                toggleChallengeState(challenge.name)
            }

            itemView.challenge_list_icon.background = challenge.icon
            itemView.challenge_list_type.text = challenge.name

        }

        /**
         * Toggle whether or not a [challenge] should be active in the coming practice session
         */
        private fun toggleChallengeState(challenge: String) {
            val currentState = sharedPref.getBoolean(challenge, false)
            sharedPref.edit()
                    .putBoolean(challenge, !currentState)
                    .apply()
            itemView.challenge_list_check.isChecked = !currentState
        }
    }
}
package com.arsalabangash.boltz.practice.models

import java.util.*

data class PracticeOptions(
        val practiceChallenges: ArrayList<String>,
        val level: String,
        val questionCount: Int,
        val isVolumeOn: Boolean,
        val showPracticeTutorial: Boolean,
        val isPremiumUser: Boolean
)
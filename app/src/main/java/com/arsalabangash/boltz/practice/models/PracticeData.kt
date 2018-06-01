package com.arsalabangash.boltz.practice.models

import java.util.*

data class PracticeData(
        val challengeModels: ArrayList<ChallengeModel>,
        val correctCount: Int,
        val attemptCount: Int,
        val totalTime: Long,
        val totalXP: Long,
        val didComplete: Boolean)
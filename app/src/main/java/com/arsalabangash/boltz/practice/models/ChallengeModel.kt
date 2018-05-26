package com.arsalabangash.boltz.practice.models


class ChallengeModel(
        var types: List<String>? = null,
        var time: Long? = null,
        var xp: Long? = null,
        var attempts: Int? = null,
        var solved: Boolean? = null
) {
    fun addAttempt() {
        attempts = attempts!! + 1
    }
}
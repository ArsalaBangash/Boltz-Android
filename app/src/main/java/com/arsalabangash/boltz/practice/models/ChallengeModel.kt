package com.arsalabangash.boltz.practice.models

/**
 * Used to store information about a challenge to then update the backend data at the end
 * of the session
 */
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
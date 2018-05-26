package com.arsalabangash.boltz.practice.models


data class ChallengeTypeModel(
        var type: String,
        var count: Long,
        var attempts: Long,
        var time: Long,
        var xp: Long
)
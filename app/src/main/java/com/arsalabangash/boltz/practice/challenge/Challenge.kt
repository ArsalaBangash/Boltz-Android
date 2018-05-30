package com.arsalabangash.boltz.practice.challenge

import com.arsalabangash.boltz.practice.models.ChallengeModel
import com.arsalabangash.boltz.practice.engine.enums.Level

abstract class Challenge(level: Level, challengeUtils: ChallengeUtils) {

    var time: Int = 15
    var layoutID: Int? = null
    var infixRepr: String? = null
    var latexRepr: String? = null
    var model: ChallengeModel = ChallengeModel()
    var checkStrategy: (String?, String?) -> Boolean = challengeUtils.engineCheckStrategy
    var label: String? = null

    var timeTaken: Long = 0
        set(value) {
            model.time = value
        }

    fun initialize() {
        setQuestion()
        setTime()
        setLabel()
        setLayout()
        generateInfix()
        generateLatex()
        generateModel()
    }

    abstract fun setQuestion()

    abstract fun setTime()

    abstract fun setLayout()

    abstract fun setLabel()

    abstract fun generateInfix()

    abstract fun generateLatex()

    abstract fun generateModel()

    fun addTimeTaken(time: Long) {
        timeTaken += time
    }

    fun setXP(xp: Long) {
        this.model.xp = xp
    }

    fun markSolved() {
        this.model.solved = true
    }

    fun addAttempt() {
        this.model.addAttempt()
    }


}
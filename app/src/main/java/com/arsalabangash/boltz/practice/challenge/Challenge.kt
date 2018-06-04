package com.arsalabangash.boltz.practice.challenge

import com.arsalabangash.boltz.practice.engine.enums.Level
import com.arsalabangash.boltz.practice.models.ChallengeModel

/**
 * All types of Challenge must inherit from this abstract class to ensure that
 * everything is in order during the practice sessions
 */
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

    /**
     * Each Challenge needs a way of generating a question. This can be in any format possible
     */
    abstract fun setQuestion()

    /**
     * Sets reasonable times for a particular type of Challenge. This will determine how long it
     * takes for the XP bar to be drained
     */
    abstract fun setTime()

    /**
     * Sets the layout ID corresponding to this Challenge, which is then inflated to create
     * a ChallengeView
     */
    abstract fun setLayout()

    /**
     * Sets a label for this Challenge to guide the user to what they should do
     */
    abstract fun setLabel()

    /**
     * Generates an infix string representation of the Challenge's question to allow the
     * math engine to evaluate
     */
    abstract fun generateInfix()

    /**
     * Generates the Latex representation for this Challenge so that the Latex View can render the
     * question
     */
    abstract fun generateLatex()

    /**
     * Generates the ChallengeModel for this Challenge
     */
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
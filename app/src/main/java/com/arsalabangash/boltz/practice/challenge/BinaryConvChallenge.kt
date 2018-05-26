package com.arsalabangash.boltz.practice.challenge

import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.models.ChallengeModel
import com.arsalabangash.boltzengine.engine.BinaryChallenge
import com.arsalabangash.boltzengine.engine.enums.Level
import com.arsalabangash.boltzengine.engine.generateBinaryQuestion

class BinaryConvChallenge(val level: Level, val challengeUtils: ChallengeUtils) :
        Challenge(level, challengeUtils) {

    private lateinit var question: BinaryChallenge

    init {
        setQuestion()
        setTime()
        setLayout()
        setLabel()
        generateInfix()
        generateLatex()
        generateModel()
    }

    override fun setQuestion() {
        question = generateBinaryQuestion(level)
    }

    override fun setTime() {
        when (level) {
            Level.Basic -> time = 35
            Level.Normal -> time = 45
            Level.Advanced -> time = 60
        }
    }

    override fun setLayout() {
        if (question.binaryToDec) {
            layoutID = R.layout.challenge_view_classic
        } else {
            layoutID = R.layout.challenge_view_decimal_to_binary
        }
    }

    override fun setLabel() {
        if (question.binaryToDec) {
            label = "Convert from Binary to Decimal:"
        } else {
            label = "Convert to Binary:"
        }
    }

    override fun generateInfix() {
        if (question.binaryToDec) {
            infixRepr = question.decimal
        } else {
            infixRepr = question.binary
        }
    }

    override fun generateLatex() {
        if (question.binaryToDec) {
            latexRepr = question.binary
        } else {
            latexRepr = question.decimal
        }
    }

    override fun generateModel() {
        this.model = ChallengeModel(types = listOf("Binary Conversion"),
                time = 0, attempts = 0, solved = false)
    }
}
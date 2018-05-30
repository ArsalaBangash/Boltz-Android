package com.arsalabangash.boltz.practice.challenge

import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.models.ChallengeModel
import com.arsalabangash.boltz.practice.engine.HexChallenge
import com.arsalabangash.boltz.practice.engine.enums.Level
import com.arsalabangash.boltz.practice.engine.generateHexQuestion

class HexConvChallenge(val level: Level, val challengeUtils: ChallengeUtils) :
        Challenge(level, challengeUtils) {

    private lateinit var question: HexChallenge

    val hexCheckStrategy: (String?, String?) -> Boolean = { userAnswer, challengeAnswer ->
        try {
            Integer.valueOf(userAnswer, 16) == Integer.valueOf(challengeAnswer!!
                    .removePrefix("0x"), 16)
        } catch (e: Exception) {
            false
        }
    }

    init {
        setQuestion()
        setTime()
        setLayout()
        setLabel()
        generateInfix()
        generateLatex()
        generateModel()
        this.checkStrategy = hexCheckStrategy
    }

    override fun setQuestion() {
        question = generateHexQuestion(level)
    }

    override fun setTime() {
        when (level) {
            Level.Basic -> time = 35
            Level.Normal -> time = 45
            Level.Advanced -> time = 60
        }
    }

    override fun setLayout() {
        if (question.hexToDec) {
            layoutID = R.layout.challenge_view_classic
        } else {
            layoutID = R.layout.challenge_view_hex
        }
    }

    override fun setLabel() {
        if (question.hexToDec) {
            label = "Convert from Hexadecimal to Decimal:"
        } else {
            label = "Convert to Hexadecimal:"
        }
    }

    override fun generateInfix() {
        if (question.hexToDec) {
            infixRepr = question.decimal
        } else {
            infixRepr = question.hex
        }
    }

    override fun generateLatex() {
        if (question.hexToDec) {
            latexRepr = question.hex
        } else {
            latexRepr = question.decimal
        }
    }

    override fun generateModel() {
        this.model = ChallengeModel(types = listOf("Hexadecimal Conversion"),
                time = 0, attempts = 0, solved = false)
    }
}
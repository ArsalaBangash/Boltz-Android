package com.arsalabangash.boltz.practice.challenge

import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.models.ChallengeModel
import com.arsalabangash.boltz.practice.engine.FactorizationGenerator
import com.arsalabangash.boltz.practice.engine.enums.Level


class FactorizationChallenge(val level: Level, val challengeUtils: ChallengeUtils) :
        Challenge(level, challengeUtils) {


    private var question: FactorizationGenerator.FactorParams? = null

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
        question = challengeUtils.factorizationGenerator.generateFactorParams(level)
    }

    override fun setTime() {
        when (level) {
            Level.Basic -> time = 35
            Level.Normal -> time = 45
            Level.Advanced -> time = 60
        }
    }

    override fun setLayout() {
        layoutID = R.layout.challenge_view_factorization
    }

    override fun setLabel() {
        label = "Factorize:"
    }

    override fun generateInfix() {
        infixRepr = challengeUtils.infixConverter.factorizationToInfix(question!!)
    }

    override fun generateLatex() {
        val expanded = challengeUtils.exprEvaluator.evaluate("expand($infixRepr)").toString()
        latexRepr = challengeUtils.latexConverter.factorToLatex(
                challengeUtils.factorizationGenerator.getExpandedParams(expanded))
    }

    override fun generateModel() {
        this.model = ChallengeModel(types = listOf("Factorization"),
                time = 0, attempts = 0, solved = false)
    }
}
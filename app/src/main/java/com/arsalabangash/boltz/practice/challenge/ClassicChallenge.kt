package com.arsalabangash.boltz.practice.challenge

import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.models.ChallengeModel
import com.arsalabangash.boltz.practice.engine.enums.Level
import com.arsalabangash.boltz.practice.engine.enums.MathOperation
import com.arsalabangash.boltz.practice.engine.expressions.ExprToken
import java.util.*

class ClassicChallenge(val level: Level, val utils: ChallengeUtils,
                       val mathOps: ArrayList<MathOperation>) : Challenge(level, utils) {


    private lateinit var question: ArrayList<ExprToken>
    private lateinit var ops: ArrayList<MathOperation>
    private lateinit var types: List<String>

    init {
        initialize()
    }

    override fun setQuestion() {
        var numberOps = Random().nextInt(2) + 1
        ops = arrayListOf<MathOperation>()
        while (numberOps > 0) {
            ops.add(mathOps[Random().nextInt(mathOps.size)])
            numberOps--
        }
        types = ops.map { it.toString() }
        this.question = utils.generate(ops, level)
    }

    override fun setTime() {
        when (level) {
            Level.Basic -> time = 15
            Level.Normal -> time = 20
            Level.Advanced -> time = 25
        }
    }

    override fun setLayout() {
        this.layoutID = R.layout.challenge_view_classic
    }

    override fun setLabel() {
        label = "Solve:"
    }

    override fun generateInfix() {
        this.infixRepr = utils.convertInfix(this.question)
    }

    override fun generateLatex() {
        this.latexRepr = this.utils.convertLatex(this.question)
    }

    override fun generateModel() {
        this.model = ChallengeModel(types = types,
                time = 0, attempts = 0, solved = false)
    }


}
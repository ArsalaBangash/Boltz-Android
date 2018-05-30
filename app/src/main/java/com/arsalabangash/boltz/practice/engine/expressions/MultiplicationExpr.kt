package com.arsalabangash.boltz.practice.engine.expressions

import com.arsalabangash.boltz.practice.engine.enums.Level
import com.arsalabangash.boltz.practice.engine.enums.MathOperation
import com.arsalabangash.boltz.practice.engine.enums.SubExprLocation
import com.arsalabangash.boltz.practice.engine.generatePositiveRandom
import java.util.*
import kotlin.math.abs

class MultiplicationExpr(mathOperation: MathOperation) : MathExpr(mathOperation) {

    internal var multiples: List<IntArray> = ArrayList()

    override fun setMax(level: Level) {
        when (level) {
            Level.Basic -> MAX = 5
            Level.Normal -> MAX = 9
            Level.Advanced -> MAX = 12
        }
    }

    override fun addZeroBoundTokens(subExprLocation: SubExprLocation) {
        val operand1 = generatePositiveRandom(MAX)
        val operand2 = 0
        when (subExprLocation) {
            SubExprLocation.NEITHER -> {
                expression.add(ExprToken(operand1))
                expression.add(ExprToken(operand2))
            }
            SubExprLocation.BOTH -> {
                expression.add(ExprToken(operand1, hasSubExpr = true))
                expression.add(ExprToken(operand2, hasSubExpr = true))
            }
            SubExprLocation.LEFT -> {
                expression.add(ExprToken(operand1, hasSubExpr = true))
                expression.add(ExprToken(operand2))
            }
            SubExprLocation.RIGHT -> {
                expression.add(ExprToken(operand1))
                expression.add(ExprToken(operand2, hasSubExpr = true))
            }
        }
    }


    override fun produceExpression(subExprLocation: SubExprLocation): ArrayList<ExprToken> {
        if (this.isBounded) {
            val absBound = abs(bound!!)
            multiples = (1..absBound)
                    .toList()
                    .filter { absBound % it == 0 }
                    .map { intArrayOf(it, absBound / it) }
                    .map {
                        if (bound!! < 0) {
                            if (randomGenerator.nextBoolean()) {
                                intArrayOf(it[0], it[1] * -1)
                            } else {
                                intArrayOf(it[0] * -1, it[1])
                            }
                        } else intArrayOf(it[0], it[1])
                    }
        }
        return super.produceExpression(subExprLocation)
    }

    override fun noSubExpressions() {
        if (this.isBounded) {
            val boundMultiples = this.multiples[randomGenerator.nextInt(this.multiples.size)]
            expression.add(ExprToken(boundMultiples[0]))
            expression.add(ExprToken(boundMultiples[1]))
        } else {
            expression.add(ExprToken(generatePositiveRandom(MAX) + 1))
            expression.add(ExprToken(generatePositiveRandom(MAX) + 1))
        }
    }

    override fun twoSubExpressions() {
        if (this.isBounded) {
            val boundMultiples = this.multiples[randomGenerator.nextInt(this.multiples.size)]
            expression.add(ExprToken(boundMultiples[0], hasSubExpr = true))
            expression.add(ExprToken(boundMultiples[1], hasSubExpr = true))
        } else {
            expression.add(ExprToken(generatePositiveRandom(MAX) + 1, hasSubExpr = true))
            expression.add(ExprToken(generatePositiveRandom(MAX) + 1, hasSubExpr = true))
        }
    }

    override fun oneSubExpression(subExprLocation: SubExprLocation) {
        when (subExprLocation) {
            SubExprLocation.LEFT -> if (this.isBounded) {
                val boundMultiples = this.multiples[randomGenerator.nextInt(this.multiples.size)]
                expression.add(ExprToken(boundMultiples[0], hasSubExpr = true))
                expression.add(ExprToken(boundMultiples[1]))
            } else {
                expression.add(ExprToken(generatePositiveRandom(MAX) + 1, hasSubExpr = true))
                expression.add(ExprToken(generatePositiveRandom(MAX) + 1))
            }
            SubExprLocation.RIGHT -> if (this.isBounded) {
                val boundMultiples = this.multiples[randomGenerator.nextInt(this.multiples.size)]
                expression.add(ExprToken(boundMultiples[0]))
                expression.add(ExprToken(boundMultiples[1], hasSubExpr = true))
            } else {
                expression.add(ExprToken(generatePositiveRandom(MAX) + 1))
                expression.add(ExprToken(generatePositiveRandom(MAX) + 1, hasSubExpr = true))
            }
        }
    }


}
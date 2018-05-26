package com.arsalabangash.boltzengine.engine.expressions

import com.arsalabangash.boltzengine.engine.enums.Level
import com.arsalabangash.boltzengine.engine.enums.MathOperation
import com.arsalabangash.boltzengine.engine.enums.SubExprLocation
import com.arsalabangash.boltzengine.engine.generatePositiveRandom
import java.util.*

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
            multiples = (1..bound!!)
                    .toList()
                    .filter { it % bound!! == 0 }
                    .map { intArrayOf(it, it / bound!!) }
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
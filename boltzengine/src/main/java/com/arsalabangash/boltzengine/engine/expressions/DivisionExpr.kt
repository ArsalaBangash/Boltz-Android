package com.arsalabangash.boltzengine.engine.expressions

import com.arsalabangash.boltzengine.engine.enums.Level
import com.arsalabangash.boltzengine.engine.enums.MathOperation
import com.arsalabangash.boltzengine.engine.enums.SubExprLocation
import com.arsalabangash.boltzengine.engine.generatePositiveRandom

class DivisionExpr(mathOperation: MathOperation) : MathExpr(mathOperation) {

    override fun setMax(level: Level) {
        when (level) {
            Level.Basic -> {
                MIN = 1
                MAX = 5
            }
            Level.Normal -> {
                MIN = 2
                MAX = 10
            }
            Level.Advanced -> {
                MIN = 2
                MAX = 13
            }
        }
    }

    override fun addZeroBoundTokens(subExprLocation: SubExprLocation) {
        val operand1 = 0
        val operand2 = generatePositiveRandom(MAX) + 1
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

    internal fun generateDivisor(bound: Int): Int {
        if (bound > MAX * MAX) return 2
        else return randomGenerator.nextInt(MAX * MAX / bound) + 1
    }

    override fun noSubExpressions() {
        if (this.isBounded) {
            val divisor = generateDivisor(bound!!)
            expression.add(ExprToken(bound!! * divisor))
            expression.add(ExprToken(divisor))
        } else {
            val quotient = generatePositiveRandom(MIN, MAX)
            val divisor = generatePositiveRandom(MIN, MAX)
            expression.add(ExprToken(quotient * divisor))
            expression.add(ExprToken(divisor))
        }
    }

    override fun twoSubExpressions() {
        if (this.isBounded) {
            val divisor = generateDivisor(bound!!)
            expression.add(ExprToken(bound!! * divisor, hasSubExpr = true))
            expression.add(ExprToken(divisor, hasSubExpr = true))
        } else {
            val quotient = generatePositiveRandom(MIN, MAX)
            val divisor = generatePositiveRandom(MIN, MAX)
            expression.add(ExprToken(quotient * divisor, hasSubExpr = true))
            expression.add(ExprToken(divisor, hasSubExpr = true))
        }
    }

    override fun oneSubExpression(subExprLocation: SubExprLocation) {
        when (subExprLocation) {
            SubExprLocation.LEFT -> if (this.isBounded) {
                val divisor = generateDivisor(bound!!)
                expression.add(ExprToken(bound!! * divisor, hasSubExpr = true))
                expression.add(ExprToken(divisor))
            } else {
                val quotient = generatePositiveRandom(MIN, MAX)
                val divisor = generatePositiveRandom(MIN, MAX)
                expression.add(ExprToken(quotient * divisor, hasSubExpr = true))
                expression.add(ExprToken(divisor))
            }
            SubExprLocation.RIGHT -> if (this.isBounded) {
                val divisor = generateDivisor(bound!!)
                expression.add(ExprToken(bound!! * divisor))
                expression.add(ExprToken(divisor, hasSubExpr = true))
            } else {
                val quotient = generatePositiveRandom(MIN, MAX)
                val divisor = generatePositiveRandom(MIN, MAX)
                expression.add(ExprToken(quotient * divisor))
                expression.add(ExprToken(divisor, hasSubExpr = true))
            }
        }
    }
}
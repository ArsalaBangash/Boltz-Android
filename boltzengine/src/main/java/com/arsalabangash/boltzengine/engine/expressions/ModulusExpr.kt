package com.arsalabangash.boltzengine.engine.expressions

import com.arsalabangash.boltzengine.engine.enums.Level
import com.arsalabangash.boltzengine.engine.enums.MathOperation
import com.arsalabangash.boltzengine.engine.enums.SubExprLocation
import com.arsalabangash.boltzengine.engine.generatePositiveRandom


class ModulusExpr(mathOperation: MathOperation) : MathExpr(mathOperation) {

    override fun setMax(level: Level) {
        when (level) {
            Level.Basic -> {
                MIN = 1
                MAX = 3
            }
            Level.Normal -> {
                MIN = 2
                MAX = 6
            }
            Level.Advanced -> {
                MIN = 2
                MAX = 10
            }
        }
    }


    override fun addZeroBoundTokens(subExprLocation: SubExprLocation) {
        val constant = generatePositiveRandom(MIN, MAX)
        val divisor = generatePositiveRandom(MIN, MAX)
        when (subExprLocation) {
            SubExprLocation.NEITHER -> {
                expression.add(ExprToken(constant * divisor))
                expression.add(ExprToken(divisor))
            }
            SubExprLocation.BOTH -> {
                expression.add(ExprToken(constant * divisor, hasSubExpr = true))
                expression.add(ExprToken(divisor, hasSubExpr = true))
            }
            SubExprLocation.LEFT -> {
                expression.add(ExprToken(constant * divisor, hasSubExpr = true))
                expression.add(ExprToken(divisor))
            }
            SubExprLocation.RIGHT -> {
                expression.add(ExprToken(constant * divisor))
                expression.add(ExprToken(divisor, hasSubExpr = true))
            }
        }

    }

    override fun noSubExpressions() {
        if (this.isBounded) {
            val divisor = generatePositiveRandom(bound!! + 1, MAX)
            val dividend = (divisor * generatePositiveRandom(MAX)) + bound!!
            expression.add(ExprToken(dividend))
            expression.add(ExprToken(divisor))
        } else {
            val divisor = generatePositiveRandom(MIN, MAX)
            val dividend = generatePositiveRandom(MIN, MAX) + divisor
            expression.add(ExprToken(dividend))
            expression.add(ExprToken(divisor))
        }
    }

    override fun twoSubExpressions() {
        if (this.isBounded) {
            val divisor = generatePositiveRandom(bound!!, MAX)
            val dividend = (divisor * generatePositiveRandom(MAX)) + bound!!
            expression.add(ExprToken(dividend, hasSubExpr = true))
            expression.add(ExprToken(divisor, hasSubExpr = true))
        } else {
            val divisor = generatePositiveRandom(MIN, MAX)
            val dividend = generatePositiveRandom(MIN, MAX) + divisor
            expression.add(ExprToken(dividend, hasSubExpr = true))
            expression.add(ExprToken(divisor, hasSubExpr = true))
        }
    }

    override fun oneSubExpression(subExprLocation: SubExprLocation) {
        when (subExprLocation) {
            SubExprLocation.LEFT -> if (this.isBounded) {
                val divisor = generatePositiveRandom(bound!!, MAX)
                val dividend = (divisor * generatePositiveRandom(MAX)) + bound!!
                expression.add(ExprToken(dividend, hasSubExpr = true))
                expression.add(ExprToken(divisor))
            } else {
                val divisor = generatePositiveRandom(MIN, MAX)
                val dividend = generatePositiveRandom(MIN, MAX) + divisor
                expression.add(ExprToken(dividend, hasSubExpr = true))
                expression.add(ExprToken(divisor))
            }
            SubExprLocation.RIGHT -> if (this.isBounded) {
                val divisor = generatePositiveRandom(bound!!, MAX)
                val dividend = (divisor * generatePositiveRandom(MAX)) + bound!!
                expression.add(ExprToken(dividend))
                expression.add(ExprToken(divisor, hasSubExpr = true))
            } else {
                val divisor = generatePositiveRandom(MIN, MAX)
                val dividend = generatePositiveRandom(MIN, MAX) + divisor
                expression.add(ExprToken(dividend))
                expression.add(ExprToken(divisor, hasSubExpr = true))
            }
        }
    }


}
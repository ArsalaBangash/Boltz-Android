package com.arsalabangash.boltzengine.engine.expressions

import com.arsalabangash.boltzengine.engine.enums.Level
import com.arsalabangash.boltzengine.engine.enums.MathOperation
import com.arsalabangash.boltzengine.engine.enums.SubExprLocation
import com.arsalabangash.boltzengine.engine.generatePositiveRandom

class SubtractionExpr(mathOperation: MathOperation) : MathExpr(mathOperation) {

    override fun setMax(level: Level) {
        when (level) {
            Level.Basic -> {
                MIN = 1
                MAX = 10
            }
            Level.Normal -> {
                MIN = 5
                MAX = 20
            }
            Level.Advanced -> {
                MIN = 20
                MAX = 100
            }
        }
    }

    override fun addZeroBoundTokens(subExprLocation: SubExprLocation) {
        val operand = generatePositiveRandom(MAX)
        when (subExprLocation) {
            SubExprLocation.NEITHER -> {
                expression.add(ExprToken(operand))
                expression.add(ExprToken(operand))
            }
            SubExprLocation.BOTH -> {
                expression.add(ExprToken(operand, hasSubExpr = true))
                expression.add(ExprToken(operand, hasSubExpr = true))
            }
            SubExprLocation.LEFT -> {
                expression.add(ExprToken(operand, hasSubExpr = true))
                expression.add(ExprToken(operand))
            }
            SubExprLocation.RIGHT -> {
                expression.add(ExprToken(operand))
                expression.add(ExprToken(operand, hasSubExpr = true))
            }
        }
    }

    override fun noSubExpressions() {
        if (this.isBounded) {
            val boundedRandom: Int = generatePositiveRandom(bound!!)
            expression.add(ExprToken(boundedRandom + bound!!))
            expression.add(ExprToken(boundedRandom))
        } else {
            expression.add(ExprToken(generatePositiveRandom(MAX)))
            expression.add(ExprToken(generatePositiveRandom(MAX)))
        }
    }

    override fun twoSubExpressions() {
        if (this.isBounded) {
            val boundedRandom = generatePositiveRandom(bound!!)
            val boundedRandomComplement = bound!! + boundedRandom
            expression.add(ExprToken(boundedRandomComplement, hasSubExpr = true))
            expression.add(ExprToken(boundedRandom, hasSubExpr = true))
        } else {
            expression.add(ExprToken(hasSubExpr = true))
            expression.add(ExprToken(hasSubExpr = true))
        }
    }

    override fun oneSubExpression(subExprLocation: SubExprLocation) {

        when (subExprLocation) {
            SubExprLocation.LEFT -> {
                if (this.isBounded) {
                    val boundedRandom = generatePositiveRandom(bound!!)
                    val boundedRandomComplement = bound!! + boundedRandom
                    expression.add(ExprToken(boundedRandomComplement, hasSubExpr = true))
                    expression.add(ExprToken(boundedRandom))
                } else {
                    expression.add(ExprToken(hasSubExpr = true))
                    expression.add(ExprToken(generatePositiveRandom(MAX)))
                }
            }
            SubExprLocation.RIGHT -> {
                if (this.isBounded) {
                    val boundedRandom = generatePositiveRandom(bound!!)
                    val boundedRandomComplement = bound!! + boundedRandom
                    expression.add(ExprToken(boundedRandomComplement))
                    expression.add(ExprToken(boundedRandom, hasSubExpr = true))
                } else {
                    expression.add(ExprToken(generatePositiveRandom(MAX)))
                    expression.add(ExprToken(hasSubExpr = true))
                }
            }
            else -> return
        }
    }

}
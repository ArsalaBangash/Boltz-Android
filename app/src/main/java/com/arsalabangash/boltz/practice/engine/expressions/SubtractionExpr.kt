package com.arsalabangash.boltz.practice.engine.expressions

import com.arsalabangash.boltz.practice.engine.enums.Level
import com.arsalabangash.boltz.practice.engine.enums.MathOperation
import com.arsalabangash.boltz.practice.engine.enums.SubExprLocation
import com.arsalabangash.boltz.practice.engine.generatePositiveRandom
import kotlin.math.abs

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
            if (bound!! < 0) {
                val boundedRandom: Int = generatePositiveRandom(abs(bound!!))
                expression.add(ExprToken(boundedRandom))
                expression.add(ExprToken(boundedRandom + abs(bound!!)))
            } else {
                val boundedRandom: Int = generatePositiveRandom(bound!!)
                expression.add(ExprToken(boundedRandom + bound!!))
                expression.add(ExprToken(boundedRandom))
            }

        } else {
            expression.add(ExprToken(generatePositiveRandom(MAX)))
            expression.add(ExprToken(generatePositiveRandom(MAX)))
        }
    }

    override fun twoSubExpressions() {
        if (this.isBounded) {
            val boundedRandom = generatePositiveRandom(abs(bound!!))
            val boundedRandomComplement = abs(bound!!) + boundedRandom
            if (bound!! > 0) {
                expression.add(ExprToken(boundedRandomComplement, hasSubExpr = true))
                expression.add(ExprToken(boundedRandom))
            } else {
                expression.add(ExprToken(boundedRandom))
                expression.add(ExprToken(boundedRandomComplement, hasSubExpr = true))
            }
        } else {
            expression.add(ExprToken(hasSubExpr = true))
            expression.add(ExprToken(hasSubExpr = true))
        }
    }

    override fun oneSubExpression(subExprLocation: SubExprLocation) {

        when (subExprLocation) {
            SubExprLocation.LEFT -> {
                if (this.isBounded) {
                    val boundedRandom = generatePositiveRandom(abs(bound!!))
                    val boundedRandomComplement = abs(bound!!) + boundedRandom
                    if (bound!! > 0) {
                        expression.add(ExprToken(boundedRandomComplement, hasSubExpr = true))
                        expression.add(ExprToken(boundedRandom))
                    } else {
                        expression.add(ExprToken(boundedRandom))
                        expression.add(ExprToken(boundedRandomComplement, hasSubExpr = true))
                    }
                } else {
                    expression.add(ExprToken(hasSubExpr = true))
                    expression.add(ExprToken(generatePositiveRandom(MAX)))
                }
            }
            SubExprLocation.RIGHT -> {
                if (this.isBounded) {
                    val boundedRandom = generatePositiveRandom(abs(bound!!))
                    val boundedRandomComplement = abs(bound!!) + boundedRandom
                    if (bound!! > 0) {
                        expression.add(ExprToken(boundedRandomComplement))
                        expression.add(ExprToken(boundedRandom, hasSubExpr = true))
                    } else {
                        expression.add(ExprToken(boundedRandom, hasSubExpr = true))
                        expression.add(ExprToken(boundedRandomComplement))
                    }
                } else {
                    expression.add(ExprToken(generatePositiveRandom(MAX)))
                    expression.add(ExprToken(hasSubExpr = true))
                }
            }
            else -> return
        }
    }

}
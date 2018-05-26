package com.arsalabangash.boltzengine.engine.expressions

import com.arsalabangash.boltzengine.engine.enums.Level
import com.arsalabangash.boltzengine.engine.enums.MathOperation
import com.arsalabangash.boltzengine.engine.enums.SubExprLocation
import com.arsalabangash.boltzengine.engine.generatePositiveRandom

class AdditionExpr(mathOperation: MathOperation) : MathExpr(mathOperation) {

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
        when (subExprLocation) {
            SubExprLocation.NEITHER -> {
                expression.add(ExprToken(0))
                expression.add(ExprToken(0))
            }
            SubExprLocation.BOTH -> {
                expression.add(ExprToken(0, hasSubExpr = true))
                expression.add(ExprToken(0, hasSubExpr = true))
            }
            SubExprLocation.LEFT -> {
                expression.add(ExprToken(0, hasSubExpr = true))
                expression.add(ExprToken(0))
            }
            SubExprLocation.RIGHT -> {
                expression.add(ExprToken(0))
                expression.add(ExprToken(0, hasSubExpr = true))
            }
        }

    }

    override fun noSubExpressions() {
        if (this.isBounded) {
            val boundedRandom = generatePositiveRandom(MIN, bound!!)
            expression.add(ExprToken(boundedRandom))
            expression.add(ExprToken(bound!! - boundedRandom))
        } else {
            expression.add(ExprToken(generatePositiveRandom(MIN, MAX)))
            expression.add(ExprToken(generatePositiveRandom(MIN, MAX)))
        }
    }

    override fun twoSubExpressions() {
        if (this.isBounded) {
            val boundedRandom = generatePositiveRandom(MIN, bound!!)
            val boundedRandomComplement = bound!! - boundedRandom
            expression.add(ExprToken(boundedRandom, hasSubExpr = true))
            expression.add(ExprToken(boundedRandomComplement, hasSubExpr = true))
        } else {
            expression.add(ExprToken(hasSubExpr = true))
            expression.add(ExprToken(hasSubExpr = true))
        }
    }

    override fun oneSubExpression(subExprLocation: SubExprLocation) {
        when (subExprLocation) {
            SubExprLocation.LEFT -> if (this.isBounded) {
                val boundedRandom = generatePositiveRandom(MIN, bound!!)
                val boundedRandomComplement = bound!! - boundedRandom
                expression.add(ExprToken(boundedRandom, hasSubExpr = true))
                expression.add(ExprToken(boundedRandomComplement))
            } else {
                expression.add(ExprToken(hasSubExpr = true))
                expression.add(ExprToken(generatePositiveRandom(MIN, MAX)))
            }
            SubExprLocation.RIGHT -> if (this.isBounded) {
                val boundedRandom = generatePositiveRandom(MIN, bound!!)
                val boundedRandomComplement = bound!! - boundedRandom
                expression.add(ExprToken(boundedRandom))
                expression.add(ExprToken(boundedRandomComplement, hasSubExpr = true))
            } else {
                expression.add(ExprToken(generatePositiveRandom(MIN, MAX)))
                expression.add(ExprToken(hasSubExpr = true))
            }
            else -> return
        }
    }


}
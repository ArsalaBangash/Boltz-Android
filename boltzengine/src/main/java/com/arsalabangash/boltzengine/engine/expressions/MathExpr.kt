package com.arsalabangash.boltzengine.engine.expressions

import com.arsalabangash.boltzengine.engine.enums.Level
import com.arsalabangash.boltzengine.engine.enums.MathOperation
import com.arsalabangash.boltzengine.engine.enums.SubExprLocation
import java.util.*

abstract class MathExpr(val mathOperation: MathOperation) {

    internal var randomGenerator = Random()
    var isBounded = false
    internal var expression = ArrayList<ExprToken>()
    var bound: Int? = null
    var MAX: Int = 0
    var MIN: Int = 0

    /**
     * Every concrete implementation of a MathExpr generates its expression in polish notation
     * by first adding it's MathOperation and then depending on the location of it's sub expressions and
     * whether or not the expression is bounded.
     *
     */
    open fun produceExpression(subExprLocation: SubExprLocation): ArrayList<ExprToken> {
        expression.add(ExprToken(mathOperation = this.mathOperation))
        if (isBounded && bound == 0) {
            this.addZeroBoundTokens(subExprLocation)
        } else {
            when (subExprLocation) {
                SubExprLocation.NEITHER -> this.noSubExpressions()
                SubExprLocation.LEFT -> this.oneSubExpression(SubExprLocation.LEFT)
                SubExprLocation.RIGHT -> this.oneSubExpression(SubExprLocation.RIGHT)
                SubExprLocation.BOTH -> this.twoSubExpressions()
            }
        }
        return expression
    }

    /**
     * Let's the implementation of MathExpr know that it is a bounded sub-expression
     */
    internal fun setBound(bound: Int) {
        this.bound = bound
        this.isBounded = true
    }

    /**
     * Adds operands to the MathExpr
     */
    internal abstract fun noSubExpressions()

    /**
     * Adds subexpressions to the MathExpr
     */
    internal abstract fun twoSubExpressions()

    /**
     * Depending on whether a ub-expression will be on the left or the right, this function will
     * add an operand and a sub-expression accordingly
     */
    internal abstract fun oneSubExpression(subExprLocation: SubExprLocation)

    /**
     * When the MathExpr is bounded by a value of 0, operands are added accordingly
     */
    internal abstract fun addZeroBoundTokens(subExprLocation: SubExprLocation)


    /**
     * Sets the maximum value for an operand based on the [difficulty] of the expression
     */
    abstract fun setMax(level: Level)

    /**
     * The ExpressionFactory is responsible for creating concrete implementations of MathExpressions
     * with the option of setting bounds on the MathExpressions
     */
    companion object ExpressionFactory {
        fun createExpression(expressionType: MathOperation, bound: Int?, level: Level): MathExpr? {
            var mathExpr: MathExpr? = null
            when (expressionType) {
                MathOperation.Addition -> mathExpr = AdditionExpr(expressionType)
                MathOperation.Subtraction -> mathExpr = SubtractionExpr(expressionType)
                MathOperation.Multiplication -> mathExpr = MultiplicationExpr(expressionType)
                MathOperation.Division -> mathExpr = DivisionExpr(expressionType)
                MathOperation.Modulus -> mathExpr = ModulusExpr(expressionType)
            }
            if (bound != null) mathExpr!!.setBound(bound)
            mathExpr!!.setMax(level)
            return mathExpr
        }
    }

}


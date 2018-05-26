package com.arsalabangash.boltzengine.engine.expressions

import com.arsalabangash.boltzengine.engine.enums.Level
import com.arsalabangash.boltzengine.engine.enums.MathOperation
import com.arsalabangash.boltzengine.engine.enums.SubExprLocation
import java.util.*

class ExprGenerator {

    val randomGenerator = Random()

    /**
     * This class is generated when an [ExprToken] with a sub-expression with it's respective [bound]
     * is found, with the original expression being split to the left and right of the
     * [ExprToken] with the sub-expression
     */
    data class ExprWithSub(val bound: Int?,
                           val leftSide: ArrayList<ExprToken>,
                           val rightSide: ArrayList<ExprToken>)

    /**
     * Reduces the question of operations left in the current expression based on the location of the
     * empty tokens processed.
     */
    fun reduceOperationsBy(subExprLocation: SubExprLocation): Int {
        when (subExprLocation) {
            SubExprLocation.LEFT, SubExprLocation.RIGHT -> return 1
            SubExprLocation.BOTH -> return 2
            else -> return 0
        }
    }

    /**
     * Based on the [operationsLeft] for a particular expression, this function will return
     * the locations for the empty tokens.
     */
    fun getEmptyTokenLocations(operationsLeft: Int): SubExprLocation {
        when (operationsLeft) {
            0, 1 ->
                return SubExprLocation.NEITHER

            2 ->
                if (randomGenerator.nextDouble() > 0.50) return SubExprLocation.LEFT
                else return SubExprLocation.RIGHT

        //An equal 1/3 chance for all EmptyTokenLocations when there are more than 2 ops left
            else -> {
                val chooseNumEmpty = randomGenerator.nextDouble()
                if (chooseNumEmpty < 0.33) return SubExprLocation.BOTH
                else if (chooseNumEmpty > 0.66) return SubExprLocation.LEFT
                else return SubExprLocation.RIGHT
            }
        }
    }

    fun generateExpression(operations: ArrayList<MathOperation>, level: Level): ArrayList<ExprToken> {
        var operationsLeft = operations.size
        var expressionList = ArrayList<ExprToken>()
        var currentOp: MathOperation = operations[randomGenerator.nextInt(operations.size)]
        var subExprLocation: SubExprLocation = getEmptyTokenLocations(operationsLeft)
        operationsLeft -= reduceOperationsBy(subExprLocation)
        operations.remove(currentOp)
        expressionList.addAll(MathExpr.createExpression(currentOp, null, level)!!
                .produceExpression(subExprLocation))
        var exprWithSub = checkSubExprTokens(expressionList)
        while (exprWithSub != null) {
            currentOp = operations[randomGenerator.nextInt(operations.size)]
            subExprLocation = getEmptyTokenLocations(operationsLeft)
            expressionList = exprWithSub.leftSide
            if (exprWithSub.bound != null) {
                expressionList.addAll((MathExpr.createExpression(currentOp, exprWithSub.bound, level) as MathExpr)
                        .produceExpression(subExprLocation))
            } else {
                expressionList.addAll(MathExpr.createExpression(currentOp, null, level)!!
                        .produceExpression(subExprLocation))
            }
            expressionList.addAll(exprWithSub.rightSide)
            operations.remove(currentOp)
            operationsLeft -= reduceOperationsBy(subExprLocation)
            exprWithSub = checkSubExprTokens(expressionList)
        }

        return expressionList
    }

    /**
     * Scans the [expressionList] for any sub-expression tokens. When one is found, the original
     * expression is converted to a [ExprWithSub]
     */
    fun checkSubExprTokens(expressionList: ArrayList<ExprToken>): ExprWithSub? {
        for (i in expressionList.indices) {
            if (expressionList[i].hasSubExpr) {
                val bound = expressionList[i].intVal
                expressionList.removeAt(i)
                val preEmptyToken = ArrayList(expressionList.subList(0, i))
                val postEmptyToken = ArrayList(expressionList.subList(i, expressionList.size))
                return ExprWithSub(bound, preEmptyToken, postEmptyToken)
            }
        }
        return null
    }
}
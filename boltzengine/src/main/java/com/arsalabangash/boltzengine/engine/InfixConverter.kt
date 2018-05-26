package com.arsalabangash.boltzengine.engine

import com.arsalabangash.boltzengine.engine.FactorizationGenerator.FactorParams
import com.arsalabangash.boltzengine.engine.enums.MathOperation
import com.arsalabangash.boltzengine.engine.expressions.ExprToken
import java.util.*


class InfixConverter {

    private val opRepresentationMap = HashMap<MathOperation, (Stack<String>) -> String>()

    init {
        opRepresentationMap.put(MathOperation.Addition, { "(${it.pop()}+${it.pop()})" })
        opRepresentationMap.put(MathOperation.Subtraction, { "(${it.pop()}-${it.pop()})" })
        opRepresentationMap.put(MathOperation.Multiplication, { "(${it.pop()}*${it.pop()})" })
        opRepresentationMap.put(MathOperation.Modulus, { "Mod(${it.pop()},${it.pop()})" })
        opRepresentationMap.put(MathOperation.Division, { "(${it.pop()}/${it.pop()})" })
    }

    fun exprToInfix(expression: ArrayList<ExprToken>): String {
        val expressionStack = Stack<String>()
        for (i in expression.indices.reversed()) {
            if (expression[i].mathOperation != null) {
                val currentOp = expression[i].mathOperation
                expressionStack.push(opRepresentationMap[currentOp]?.invoke(expressionStack))
            } else expressionStack.push(expression[i].intVal.toString())
        }
        return expressionStack.pop()
    }

    fun factorizationToInfix(params: FactorParams): String {
        return "(${params.xCoeff1}x + ${params.const1})*(${params.xCoeff2}x + ${params.const2})"
    }

}
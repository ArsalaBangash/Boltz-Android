package com.arsalabangash.boltzengine.engine

import com.arsalabangash.boltzengine.engine.enums.MathOperation
import com.arsalabangash.boltzengine.engine.expressions.ExprToken
import java.util.*

class LatexConverter {

    private val opRepresentationMap = HashMap<MathOperation, (Stack<String>) -> String>()

    private val addLatex = { exprStack: Stack<String> ->
        "(${exprStack.pop()}+${parenthesizeNegative(exprStack.pop())})"
    }

    private val subLatex = { exprStack: Stack<String> ->
        "(${exprStack.pop()}-${parenthesizeNegative(exprStack.pop())})"
    }

    private val multLatex = { exprStack: Stack<String> ->
        "(${exprStack.pop()}\\times${parenthesizeNegative(exprStack.pop())})"
    }

    private val modLatex = { exprStack: Stack<String> ->
        "(${exprStack.pop()} \\% ${parenthesizeNegative(exprStack.pop())})"
    }

    private val divLatex = divLatex@ { exprStack: Stack<String> ->
        val dividend = exprStack.pop()
        val divisor = exprStack.pop()
        if (dividend.startsWith("\\frac") || divisor.startsWith("\\frac")) {
            return@divLatex "$dividend \\div $divisor"
        } else {
            return@divLatex "\\frac{$dividend}{$divisor}"
        }
    }

    init {

        opRepresentationMap.put(MathOperation.Addition, addLatex)
        opRepresentationMap.put(MathOperation.Subtraction, subLatex)
        opRepresentationMap.put(MathOperation.Multiplication, multLatex)
        opRepresentationMap.put(MathOperation.Modulus, modLatex)
        opRepresentationMap.put(MathOperation.Division, divLatex)
    }

    fun exprToLatex(expression: ArrayList<ExprToken>): String {
        val expressionStack = Stack<String>()
        for (i in expression.indices.reversed()) {
            if (expression[i].mathOperation != null) {
                val currentOp = expression[i].mathOperation
                expressionStack.push(opRepresentationMap[currentOp]?.invoke(expressionStack))
            } else expressionStack.push(expression[i].intVal.toString())
        }
        val latexExpr = expressionStack.pop()
        if (latexExpr.startsWith("(") && latexExpr.endsWith(")")) {
            return "$$${latexExpr.substring(1, latexExpr.length - 1)}$$"
        } else return "$$$latexExpr$$"
    }

    fun factorToLatex(expandedParams: List<Int>): String {
        val factorLatex = StringBuilder("$$")
        if (expandedParams[2] == 1) {
            factorLatex.append("x^{2}")
        } else {
            factorLatex.append("${expandedParams[2]}x^{2}")
        }
        if (expandedParams[1] > 0) factorLatex.append(" + ")
        if (expandedParams[1] != 0) factorLatex.append("${expandedParams[1]}x")

        if (expandedParams[0] > 0) factorLatex.append(" + ")
        if (expandedParams[0] != 0) factorLatex.append("${expandedParams[0]}")
        return factorLatex.append("$$").toString()
    }

    private fun parenthesizeNegative(number: String): String {
        return if (number.startsWith("-")) {
            "($number)"
        } else {
            number
        }
    }
}
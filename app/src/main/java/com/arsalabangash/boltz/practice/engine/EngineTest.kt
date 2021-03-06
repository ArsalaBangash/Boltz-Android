package com.arsalabangash.boltz.practice.engine

import com.arsalabangash.boltz.practice.engine.enums.Level
import com.arsalabangash.boltz.practice.engine.enums.MathOperation
import com.arsalabangash.boltz.practice.engine.expressions.ExprGenerator
import com.arsalabangash.boltz.practice.engine.expressions.ExprToken
import java.util.*

val gen = ExprGenerator()
val infix = InfixConverter()

fun main(args: Array<String>) {
    val mathOps = arrayListOf<MathOperation>(MathOperation.Modulus)
    for (i in 0..1000) {
        val ops = getOps(mathOps)
        printExpr(gen.generateExpression(ops, Level.Advanced))
    }

}

fun printExpr(expr: ArrayList<ExprToken>) {
    println(infix.exprToInfix(expr))
}

fun getOps(mathOps: ArrayList<MathOperation>): ArrayList<MathOperation> {
    var numberOps = Random().nextInt(2) + 1
    val ops = arrayListOf<MathOperation>()
    while (numberOps > 0) {
        ops.add(mathOps[Random().nextInt(mathOps.size)])
        numberOps--
    }
    return ops
}
package com.arsalabangash.boltz.practice.engine.expressions

import com.arsalabangash.boltz.practice.engine.enums.MathOperation

/**
 * An ExprToken is either a operation or an integer operand to be used when generating
 * and evaluating expressions.
 */
class ExprToken(val intVal: Int? = null, val mathOperation: MathOperation? = null,
                val hasSubExpr: Boolean = false) {

//    override fun toString(): String {
//        if (this.intVal != null) {
//            val sb = StringBuilder()
//            if (this.hasSubExpr) sb.append("bounded ")
//            sb.append(this.intVal)
//            return sb.toString()
//        } else return this.mathOperation.toString()
//    }
}

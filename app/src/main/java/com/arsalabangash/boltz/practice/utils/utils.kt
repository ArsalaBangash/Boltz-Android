package com.arsalabangash.boltz.practice.utils

import android.content.Context
import com.arsalabangash.boltz.practice.R
import java.util.*

class TrigMapProvider(context: Context) {
    private var degreeMap: MutableMap<String, String> = HashMap()
    private var radMap: MutableMap<String, String> = HashMap()

    init {
        degreeMap.put("sin(0)", context.getString(R.string.Zero))
        degreeMap.put("cos(0)", context.getString(R.string.One))
        degreeMap.put("tan(0)", context.getString(R.string.Zero))

        degreeMap.put("sin(30)", context.getString(R.string.Half))
        degreeMap.put("cos(30)", context.getString(R.string.Root3By2))
        degreeMap.put("tan(30)", context.getString(R.string.OneByRoot3))

        degreeMap.put("sin(45)", context.getString(R.string.Root2By2))
        degreeMap.put("cos(45)", context.getString(R.string.Root2By2))
        degreeMap.put("tan(45)", context.getString(R.string.One))

        degreeMap.put("sin(60)", context.getString(R.string.Root3By2))
        degreeMap.put("cos(60)", context.getString(R.string.Half))
        degreeMap.put("tan(60)", context.getString(R.string.Root3))

        degreeMap.put("sin(90)", context.getString(R.string.One))
        degreeMap.put("cos(90)", context.getString(R.string.Zero))
        degreeMap.put("tan(90)", context.getString(R.string.Undefined))

        degreeMap.put("sin(120)", context.getString(R.string.Root3By2))
        degreeMap.put("cos(120)", context.getString(R.string.NegHalf))
        degreeMap.put("tan(120)", context.getString(R.string.NegRoot3))

        degreeMap.put("sin(135)", context.getString(R.string.Root2By2))
        degreeMap.put("cos(135)", context.getString(R.string.NegRoot2By2))
        degreeMap.put("tan(135)", context.getString(R.string.NegOne))

        degreeMap.put("sin(150)", context.getString(R.string.Half))
        degreeMap.put("cos(150)", context.getString(R.string.NegRoot3By2))
        degreeMap.put("tan(150)", context.getString(R.string.NegOneByRoot3))

        degreeMap.put("sin(180)", context.getString(R.string.Zero))
        degreeMap.put("cos(180)", context.getString(R.string.NegOne))
        degreeMap.put("tan(180)", context.getString(R.string.Zero))

        degreeMap.put("sin(210)", context.getString(R.string.NegHalf))
        degreeMap.put("cos(210)", context.getString(R.string.NegRoot3By2))
        degreeMap.put("tan(210)", context.getString(R.string.OneByRoot3))

        degreeMap.put("sin(225)", context.getString(R.string.NegRoot2By2))
        degreeMap.put("cos(225)", context.getString(R.string.NegRoot2By2))
        degreeMap.put("tan(225)", context.getString(R.string.One))

        degreeMap.put("sin(240)", context.getString(R.string.NegRoot3By2))
        degreeMap.put("cos(240)", context.getString(R.string.NegHalf))
        degreeMap.put("tan(240)", context.getString(R.string.Root3))

        degreeMap.put("sin(270)", context.getString(R.string.NegOne))
        degreeMap.put("cos(270)", context.getString(R.string.Zero))
        degreeMap.put("tan(270)", context.getString(R.string.Undefined))

        degreeMap.put("sin(300)", context.getString(R.string.NegRoot3By2))
        degreeMap.put("cos(300)", context.getString(R.string.Half))
        degreeMap.put("tan(300)", context.getString(R.string.NegRoot3))

        degreeMap.put("sin(315)", context.getString(R.string.NegRoot2By2))
        degreeMap.put("cos(315)", context.getString(R.string.Root2By2))
        degreeMap.put("tan(315)", context.getString(R.string.NegOne))

        degreeMap.put("sin(330)", context.getString(R.string.NegHalf))
        degreeMap.put("cos(330)", context.getString(R.string.NegRoot3By2))
        degreeMap.put("tan(330)", context.getString(R.string.NegOneByRoot3))

        radMap.put("sin(0)", "sin(0)")
        radMap.put("cos(0)", "cos(0)")
        radMap.put("tan(0)", "tan(0)")

        radMap.put("sin(30)", "sin(\u03C0/6)")
        radMap.put("cos(30)", "cos(\u03C0/6)")
        radMap.put("tan(30)", "tan(\u03C0/6)")

        radMap.put("sin(45)", "sin(\u03C0/4)")
        radMap.put("cos(45)", "cos(\u03C0/4)")
        radMap.put("tan(45)", "tan(\u03C0/4)")

        radMap.put("sin(60)", "sin(\u03C0/3)")
        radMap.put("cos(60)", "cos(\u03C0/3)")
        radMap.put("tan(60)", "tan(\u03C0/3)")

        radMap.put("sin(90)", "sin(\u03C0/2)")
        radMap.put("cos(90)", "cos(\u03C0/2)")
        radMap.put("tan(90)", "tan(\u03C0/2)")

        radMap.put("sin(120)", "sin(2\u03C0/3)")
        radMap.put("cos(120)", "cos(2\u03C0/3)")
        radMap.put("tan(120)", "tan(2\u03C0/3)")

        radMap.put("sin(135)", "sin(3\u03C0/4)")
        radMap.put("cos(135)", "cos(3\u03C0/4)")
        radMap.put("tan(135)", "tan(3\u03C0/4)")

        radMap.put("sin(150)", "sin(5\u03C0/6)")
        radMap.put("cos(150)", "cos(5\u03C0/6)")
        radMap.put("tan(150)", "tan(5\u03C0/6)")

        radMap.put("sin(180)", "sin(\u03C0)")
        radMap.put("cos(180)", "cos(\u03C0)")
        radMap.put("tan(180)", "tan(\u03C0)")

        radMap.put("sin(210)", "sin(7\u03C0/6)")
        radMap.put("cos(210)", "cos(7\u03C0/6)")
        radMap.put("tan(210)", "tan(7\u03C0/6)")

        radMap.put("sin(225)", "sin(5\u03C0/4)")
        radMap.put("cos(225)", "cos(5\u03C0/4)")
        radMap.put("tan(225)", "tan(5\u03C0/4)")

        radMap.put("sin(240)", "sin(4\u03C0/3)")
        radMap.put("cos(240)", "cos(4\u03C0/3)")
        radMap.put("tan(240)", "tan(4\u03C0/3)")

        radMap.put("sin(270)", "sin(3\u03C0/2)")
        radMap.put("cos(270)", "cos(3\u03C0/2)")
        radMap.put("tan(270)", "tan(3\u03C0/2)")

        radMap.put("sin(300)", "sin(5\u03C0/3)")
        radMap.put("cos(300)", "cos(5\u03C0/3)")
        radMap.put("tan(300)", "tan(5\u03C0/3)")

        radMap.put("sin(315)", "sin(7\u03C0/4)")
        radMap.put("cos(315)", "cos(7\u03C0/4)")
        radMap.put("tan(315)", "tan(7\u03C0/4)")

        radMap.put("sin(330)", "sin(11\u03C0/6)")
        radMap.put("cos(330)", "cos(11\u03C0/6)")
        radMap.put("tan(330)", "tan(11\u03C0/6)")
    }

    fun getRadianMap() = radMap

    fun getDegreeMap() = degreeMap

}

data class TrigChallengeData(var trigFunction: String?, var degrees: String?)

fun Array<String>.getRandomElement(): String {
    return this.get(Random().nextInt(this.size - 1))
}
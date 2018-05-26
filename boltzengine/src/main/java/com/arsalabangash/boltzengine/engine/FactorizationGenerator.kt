package com.arsalabangash.boltzengine.engine

import com.arsalabangash.boltzengine.engine.enums.Level

class FactorizationGenerator {

    data class FactorParams(val xCoeff1: Int, val xCoeff2: Int, val const1: Int, val const2: Int)

    private var xCoeffMax = 1
    private var constMax = 5

    fun setMax(level: Level) {
        when (level) {
            Level.Basic -> {
                xCoeffMax = 1
                constMax = 5
            }
            Level.Normal -> {
                xCoeffMax = 2
                constMax = 10
            }
            Level.Advanced -> {
                xCoeffMax = 3
                constMax = 10
            }
        }
    }

    fun generateFactorParams(level: Level): FactorParams {
        setMax(level)
        return FactorParams(generatePositiveRandom(xCoeffMax),
                generatePositiveRandom(xCoeffMax),
                generatePositiveRandom(constMax),
                generatePositiveRandom(constMax))
    }

    fun getExpandedParams(infix: String): List<Int> {
        return infix.replace("-", "+-")
                .split(Regex("[+]"))
                .filter { it.isNotEmpty() }
                .map { it.replace(Regex("(x\\^2)|[x*]"), "") }
                .map { if (it != "") it.toInt() else 1 }
    }
}
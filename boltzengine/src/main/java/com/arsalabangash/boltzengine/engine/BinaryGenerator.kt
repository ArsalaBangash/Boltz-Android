package com.arsalabangash.boltzengine.engine

import com.arsalabangash.boltzengine.engine.enums.Level
import java.util.*


data class BinaryChallenge(val decimal: String, val binary: String, val binaryToDec: Boolean)

fun generateBinaryQuestion(level: Level): BinaryChallenge {
    val biDecPair = getBiDecPair(level)
    if (Random().nextDouble() > 0.50) {
        return BinaryChallenge(biDecPair.first, biDecPair.second, true)
    } else {
        return BinaryChallenge(biDecPair.first, biDecPair.second, false)
    }
}

fun getBiDecPair(level: Level): Pair<String, String> {
    when (level) {
        Level.Basic -> {
            val decimal = (generatePositiveRandom(64) + 1)
            val binary = "0${Integer.toBinaryString(decimal)}"
            return Pair(decimal.toString(), binary)
        }
        Level.Advanced -> {
            val decimal = (generatePositiveRandom(256) + 1)
            val binary = "0${Integer.toBinaryString(decimal)}"
            return Pair(decimal.toString(), binary)
        }
        else -> {
            val decimal = (generatePositiveRandom(128) + 1)
            val binary = "0${Integer.toBinaryString(decimal)}"
            return Pair(decimal.toString(), binary)
        }
    }
}


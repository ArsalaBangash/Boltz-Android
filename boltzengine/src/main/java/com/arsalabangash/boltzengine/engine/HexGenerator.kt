package com.arsalabangash.boltzengine.engine

import com.arsalabangash.boltzengine.engine.enums.Level
import java.util.*

data class HexChallenge(val decimal: String, val hex: String, val hexToDec: Boolean)

fun generateHexQuestion(level: Level): HexChallenge {
    val hexDecPair = getHexDecPair(level)
    if (Random().nextDouble() > 0.50) {
        return HexChallenge(hexDecPair.first, hexDecPair.second, true)
    } else {
        return HexChallenge(hexDecPair.first, hexDecPair.second, false)
    }
}

fun getHexDecPair(level: Level): Pair<String, String> {
    when (level) {
        Level.Basic -> {
            val decimal = (generatePositiveRandom(32) + 1)
            val hex = "0x${Integer.toHexString(decimal)}"
            return Pair(decimal.toString(), hex)
        }
        Level.Advanced -> {
            val decimal = (generatePositiveRandom(32, 256) + 1)
            val hex = "0x${Integer.toHexString(decimal)}"
            return Pair(decimal.toString(), hex)
        }
        else -> {
            val decimal = (generatePositiveRandom(4, 128) + 1)
            val hex = "0x${Integer.toHexString(decimal)}"
            return Pair(decimal.toString(), hex)
        }
    }
}
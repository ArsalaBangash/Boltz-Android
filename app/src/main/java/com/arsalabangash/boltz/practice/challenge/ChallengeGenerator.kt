package com.arsalabangash.boltz.practice.challenge

import com.arsalabangash.boltz.practice.engine.enums.Level
import com.arsalabangash.boltz.practice.engine.enums.MathOperation
import java.util.*
import kotlin.collections.ArrayList

class ChallengeGenerator(val challengeUtils: ChallengeUtils, level: String, val challenges: ArrayList<String>) {


    private val challengeTypes = ArrayList<() -> Challenge>()
    private val classicChallengeOps = arrayListOf<MathOperation>()
    private lateinit var level: Level
    private val randomGen = Random()

    init {
        when (level) {
            "Basic" -> this.level = Level.Basic
            "Normal" -> this.level = Level.Normal
            "Advanced" -> this.level = Level.Advanced
        }
        challenges.forEach { getChallenge(it) }
    }

    fun getChallenge(challengeName: String) {
        when (challengeName) {
            "Addition" -> classicChallengeOps.add(MathOperation.Addition)
            "Subtraction" -> classicChallengeOps.add(MathOperation.Subtraction)
            "Multiplication" -> classicChallengeOps.add(MathOperation.Multiplication)
            "Division" -> classicChallengeOps.add(MathOperation.Division)
            "Modulus" -> classicChallengeOps.add(MathOperation.Modulus)
            "Factorization" -> challengeTypes.add({ FactorizationChallenge(level, challengeUtils) })
            "Binary Conversion" -> challengeTypes.add({ BinaryConvChallenge(level, challengeUtils) })
            "Hexadecimal Conversion" -> challengeTypes.add({ HexConvChallenge(level, challengeUtils) })
            "Degree Trigonometry" -> challengeTypes.add({ DegreeTrigChallenge(level, challengeUtils) })
            "Radian Trigonometry" -> challengeTypes.add({ RadianTrigChallenge(level, challengeUtils) })
        }
        if (classicChallengeOps.isNotEmpty()) {
            challengeTypes.add({ ClassicChallenge(level, challengeUtils, classicChallengeOps) })
        }
    }

    fun generateChallenge(): Challenge {
        return if (classicChallengeOps.isNotEmpty()) {
            if (randomGen.nextInt(challenges.size) < classicChallengeOps.size) {
                challengeTypes[challengeTypes.lastIndex].invoke()
            } else challengeTypes[randomGen.nextInt(challengeTypes.size - 1)].invoke()
        } else challengeTypes[randomGen.nextInt(challengeTypes.size)].invoke()
    }
}
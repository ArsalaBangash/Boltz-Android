package com.arsalabangash.boltz.practice.challenge

import com.arsalabangash.boltz.practice.BoltzPracticeApp
import com.arsalabangash.boltzengine.engine.enums.Level
import com.arsalabangash.boltzengine.engine.enums.MathOperation
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ChallengeGenerator(challengeLevel: String, val challengeNames: ArrayList<String>) {

    @Inject
    lateinit var challengeUtils: ChallengeUtils
    private val challengeTypes = ArrayList<() -> Challenge>()
    val classicChallengeOps = arrayListOf<MathOperation>()
    private lateinit var level: Level
    private val randomGen = Random()

    init {
        BoltzPracticeApp.component.inject(this)
        when (challengeLevel) {
            "Basic" -> level = Level.Basic
            "Normal" -> level = Level.Normal
            "Advanced" -> level = Level.Advanced
        }
        challengeNames.forEach { getChallenge(it) }
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
        if (classicChallengeOps.isNotEmpty()) {
            if (randomGen.nextInt(challengeNames.size) < classicChallengeOps.size) {
                return challengeTypes[challengeTypes.lastIndex].invoke()
            } else return challengeTypes[randomGen.nextInt(challengeTypes.size - 1)].invoke()
        } else return challengeTypes[randomGen.nextInt(challengeTypes.size)].invoke()
    }
}
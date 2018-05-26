package com.arsalabangash.boltzengine.engine

import java.util.*

val randomGenerator = Random()

fun generateNonZero(absoluteRange: Int): Int {
    var randomNumber = randomGenerator.nextInt(2 * absoluteRange) - absoluteRange
    if (randomNumber == 0) randomNumber++
    return randomNumber
}

fun generatePositiveRandom(min: Int, max: Int): Int = randomGenerator.nextInt(max) + min
fun generatePositiveRandom(max: Int): Int = randomGenerator.nextInt(max) + 1

fun generatePosNegRandom(range: Int): Int = randomGenerator.nextInt(range * 2) - range
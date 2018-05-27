package com.arsalabangash.boltz.practice.challenge

/**
 * The ChallengeQueue class will hold all practiceChallenges responsible for a particular Math Practice
 * session. The ChallengesViewAdapter's model will be based on this queue.
 *
 * The functions within this class wrap around the super LinkedBlockingDeque class to make
 * manipulation of the queue intuitive
 */
class ChallengeQueue : java.util.concurrent.LinkedBlockingDeque<Challenge>() {

    var numIncorrect: Int = 0
    var incorrectInRow: Int = 0

    fun getHeadChallenge(): Challenge {
        return super.peek()
    }

    fun addChallenge(challenge: Challenge) {
        super.put(challenge)
    }

    fun dequeHeadChallenge(): Challenge {
        return super.poll()
    }
}
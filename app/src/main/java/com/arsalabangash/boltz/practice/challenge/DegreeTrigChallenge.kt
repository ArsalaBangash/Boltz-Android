package com.arsalabangash.boltz.practice.challenge

import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.engine.enums.Level
import com.arsalabangash.boltz.practice.models.ChallengeModel
import com.arsalabangash.boltz.practice.utils.TrigChallengeData
import java.util.*
import kotlin.collections.HashMap

open class DegreeTrigChallenge(val level: Level, challengeUtils: ChallengeUtils) :
        Challenge(level, challengeUtils), TrigChallenge, MultipleChoiceChallenge {

    val randomGenerator = Random()
    private var question: TrigChallengeData = TrigChallengeData(null, null)
    private var answer: String? = null
    override val multipleChoices = arrayListOf<String>()

    override val degreeMap = challengeUtils.trigMapProvider.getDegreeMap()
    override val radianMap = HashMap<String, String>()

    init {
        checkStrategy = { s1, s2 ->
            try {
                s1.equals(s2)
            } catch (e: Exception) {
                false
            }
        }
        setQuestion()
        addMultipleChoices()
        setTime()
        setLayout()
        setLabel()
        generateInfix()
        generateLatex()
        generateModel()
    }

    override fun setQuestion() {
        val trigOperator = randomGenerator.nextInt(3)
        val degreeChooser = randomGenerator.nextInt(3) + 2
        var baseChooser = 0
        when (level) {
            Level.Basic -> baseChooser = randomGenerator.nextInt(2)
            Level.Normal -> baseChooser = randomGenerator.nextInt(4)
            Level.Advanced -> baseChooser = randomGenerator.nextInt(8)
        }
        val base = 90 * baseChooser
        val degrees: Int = base + 15 * degreeChooser
        question.degrees = (degrees % 360).toString()
        when (trigOperator) {
            0 -> question.trigFunction = "sin"
            1 -> question.trigFunction = "cos"
            2 -> question.trigFunction = "tan"
        }
        answer = degreeMap.get("${question.trigFunction}(${degrees % 360})")

    }

    override fun addMultipleChoices() {
        val choices = arrayListOf<String>()
        for (choice in degreeMap.values) {
            if (choice != answer) choices.add(choice)
        }
        choices.shuffle()
        multipleChoices.add(answer!!)
        (0..2).forEach {
            multipleChoices.add(choices[it])
        }

    }

    override fun setTime() {
        time = when (level) {
            Level.Basic -> 35
            Level.Normal -> 45
            Level.Advanced -> 60
        }
    }

    override fun setLayout() {
        layoutID = R.layout.challenge_view_multiple_choice
    }


    override fun setLabel() {
        label = "Solve:"
    }

    override fun generateInfix() {
        infixRepr = answer
    }

    override fun generateLatex() {
        latexRepr = "$$\\${question.trigFunction}(${question.degrees})$$"
    }

    override fun generateModel() {
        this.model = ChallengeModel(types = listOf("Degree Trigonometry"),
                time = 0, attempts = 0, solved = false)
    }
}
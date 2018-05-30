package com.arsalabangash.boltz.practice.challenge

import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.models.ChallengeModel
import com.arsalabangash.boltz.practice.utils.TrigChallengeData
import com.arsalabangash.boltz.practice.engine.enums.Level
import java.util.*


class RadianTrigChallenge(val level: Level, val challengeUtils: ChallengeUtils) :
        Challenge(level, challengeUtils) {

    private val randomGenerator = Random()
    private var question: TrigChallengeData = TrigChallengeData(null, null)
    private var answer: String? = null
    val multipleChoices = arrayListOf<String>()
    private val radMap = challengeUtils.trigMapProvider.getRadianMap()
    private val degreeMap = challengeUtils.trigMapProvider.getDegreeMap()

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
        val degrees = base + 15 * degreeChooser
        question.degrees = (degrees % 360).toString()
        when (trigOperator) {
            0 -> question.trigFunction = "sin"
            1 -> question.trigFunction = "cos"
            2 -> question.trigFunction = "tan"
        }
        answer = degreeMap.get("${question.trigFunction}(${question.degrees})")

    }

    fun addMultipleChoices() {
        val choices = degreeMap.values.filter { it != answer }
        Collections.shuffle(choices)
        multipleChoices.add(answer!!)
        (0..2).forEach {
            multipleChoices.add(choices[it])
        }

    }

    override fun setTime() {
        when (level) {
            Level.Basic -> time = 35
            Level.Normal -> time = 45
            Level.Advanced -> time = 60
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
        latexRepr = "$$\\${radMap["${question.trigFunction}(${question.degrees})"]!!.replace("π", "\\pi")}$$"
    }

    override fun generateModel() {
        this.model = ChallengeModel(types = listOf("Radian Trigonometry"),
                time = 0, attempts = 0, solved = false)
    }
}
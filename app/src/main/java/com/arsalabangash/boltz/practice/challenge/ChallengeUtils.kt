package com.arsalabangash.boltz.practice.challenge

import com.arsalabangash.boltz.practice.BoltzPracticeApp
import com.arsalabangash.boltz.practice.utils.TrigMapProvider
import com.arsalabangash.boltzengine.engine.FactorizationGenerator
import com.arsalabangash.boltzengine.engine.InfixConverter
import com.arsalabangash.boltzengine.engine.LatexConverter
import com.arsalabangash.boltzengine.engine.enums.Level
import com.arsalabangash.boltzengine.engine.enums.MathOperation
import com.arsalabangash.boltzengine.engine.expressions.ExprGenerator
import com.arsalabangash.boltzengine.engine.expressions.ExprToken
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.matheclipse.core.eval.ExprEvaluator
import org.matheclipse.core.interfaces.IExpr
import javax.inject.Inject

/**
 * This class acts as a Singleton that contains utility classes for challenge evaluation, generation
 * and visualization.
 */
class ChallengeUtils {

    @Inject
    lateinit var exprEvaluator: ExprEvaluator
    @Inject
    lateinit var infixConverter: InfixConverter
    @Inject
    lateinit var latexConverter: LatexConverter
    @Inject
    lateinit var exprGenerator: ExprGenerator
    @Inject
    lateinit var factorizationGenerator: FactorizationGenerator
    @Inject
    lateinit var trigMapProvider: TrigMapProvider

    init {
        BoltzPracticeApp.component.inject(this)
    }

    /**
     * Asynchronously calls the evaluate function of the ExprEvaluator class so that all the
     * internal classes associated with evaluation are loaded into runtime memory
     */
    fun initialize() {
        Observable.fromCallable { evaluate("1") }
                .subscribeOn(Schedulers.computation())
                .subscribe()
                .dispose()

    }

    /**
     * Takes in a [expression] string that is evaluated by the [exprEvaluator] class. If evaluation
     * returns an exception (because of a poorly formatted binary, then an exception is thrown
     */
    fun evaluate(expression: String?): IExpr? {
        try {
            return exprEvaluator.evaluate(expression)
        } catch (e: Exception) {
            throw (e)
        }
    }

    /**
     * Given [toCheck] an observable containing two strings, this function checks their result and
     * maps it to a Boolean
     */
    fun checkAnswer(userAnswer: String, challengeAnswer: String, checkStrategy: (String, String) -> Boolean): Single<Boolean> {
        return Single.fromCallable {
            checkStrategy(userAnswer, challengeAnswer)
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Returns true if the generated [expression] and the user inputted [answer] are the same,
     * otherwise returns false if an exception is caught or if the user's [answer] is incorrect
     */
    val engineCheckStrategy: (String?, String?) -> Boolean = { userAnswer, challengeAnswer ->
        try {
            evaluate("$userAnswer == $challengeAnswer").toString().toBoolean()
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Converts the [expression] into string infix notation so that it may be processed by the
     * [exprEvaluator]
     */
    fun convertInfix(expression: ArrayList<ExprToken>): String {
        return infixConverter.exprToInfix(expression)
    }

    /**
     * Converts the [expression] into string LateX representation so that it may be displayed
     * in the MathView
     */
    fun convertLatex(expression: ArrayList<ExprToken>): String {
        return latexConverter.exprToLatex(expression)
    }

    /**
     * Given a list of engine [operations], returns an expression as an ArrayList<ExprToken>
     * in Polish notation
     */
    fun generate(operations: ArrayList<MathOperation>, level: Level): ArrayList<ExprToken> {
        return exprGenerator.generateExpression(operations, level)
    }
}
package com.arsalabangash.boltz.practice.challenge

import android.content.Context
import com.arsalabangash.boltz.practice.utils.TrigMapProvider
import com.arsalabangash.boltz.practice.engine.FactorizationGenerator
import com.arsalabangash.boltz.practice.engine.InfixConverter
import com.arsalabangash.boltz.practice.engine.LatexConverter
import com.arsalabangash.boltz.practice.engine.enums.Level
import com.arsalabangash.boltz.practice.engine.enums.MathOperation
import com.arsalabangash.boltz.practice.engine.expressions.ExprGenerator
import com.arsalabangash.boltz.practice.engine.expressions.ExprToken
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.matheclipse.core.eval.ExprEvaluator
import org.matheclipse.core.interfaces.IExpr

/**
 * This class acts as a Singleton that contains utility classes for challenge evaluation, generation
 * and visualization.
 */
class ChallengeUtils {

    lateinit var exprEvaluator: ExprEvaluator
    lateinit var infixConverter: InfixConverter
    lateinit var latexConverter: LatexConverter
    lateinit var exprGenerator: ExprGenerator
    lateinit var factorizationGenerator: FactorizationGenerator
    lateinit var trigMapProvider: TrigMapProvider

    companion object {
        @Volatile
        private var INSTANCE: ChallengeUtils? = null

        fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildUtils(context).also { INSTANCE = it }
                }

        /**
         * Asynchronously calls the evaluate function of the ExprEvaluator class so that all the
         * internal classes associated with evaluation are loaded into runtime memory
         */
        private fun buildUtils(context: Context): ChallengeUtils {
            val utils = ChallengeUtils()
            utils.exprEvaluator = ExprEvaluator()
            utils.infixConverter = InfixConverter()
            utils.latexConverter = LatexConverter()
            utils.exprGenerator = ExprGenerator()
            utils.factorizationGenerator = FactorizationGenerator()
            utils.trigMapProvider = TrigMapProvider(context)
            Observable.fromCallable { utils.evaluate("1") }
                    .subscribeOn(Schedulers.computation())
                    .subscribe()
                    .dispose()
            return utils
        }

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
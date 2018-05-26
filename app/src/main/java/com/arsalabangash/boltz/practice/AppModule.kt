package com.arsalabangash.boltz.practice

import android.app.Application
import android.content.Context
import com.arsalabangash.boltz.practice.challenge.ChallengeUtils
import com.arsalabangash.boltz.practice.utils.TrigMapProvider
import com.arsalabangash.boltzengine.engine.FactorizationGenerator
import com.arsalabangash.boltzengine.engine.InfixConverter
import com.arsalabangash.boltzengine.engine.LatexConverter
import com.arsalabangash.boltzengine.engine.expressions.ExprGenerator
import dagger.Module
import dagger.Provides
import org.matheclipse.core.eval.ExprEvaluator
import javax.inject.Singleton

/**
 * A Module class for dagger 2 that provides the modules needed for injection throughout the
 * [application]
 */
@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideExprEvaluator(): ExprEvaluator = ExprEvaluator()

    @Provides
    @Singleton
    fun provideInfixConverter(): InfixConverter = InfixConverter()

    @Provides
    @Singleton
    fun provideLatexConverter(): LatexConverter = LatexConverter()

    @Provides
    @Singleton
    fun provideExpressionGenerator(): ExprGenerator = ExprGenerator()

    @Provides
    @Singleton
    fun provideFactorizationGenerator(): FactorizationGenerator = FactorizationGenerator()

    @Provides
    @Singleton
    fun provideTrigMap(): TrigMapProvider {
        return TrigMapProvider(provideApplicationContext())
    }

    @Provides
    @Singleton
    fun provideChallengeUtils(): ChallengeUtils {
        return ChallengeUtils()
    }


}
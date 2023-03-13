package rules.analyser

import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
sealed interface Analyser : BaseRule {
    fun invoke(data: Array<IntArray>): Int
}
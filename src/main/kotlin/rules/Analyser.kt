package rules

import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
sealed class Analyser : BaseRule() {
    abstract fun invoke(data: Array<IntArray>): Int
}
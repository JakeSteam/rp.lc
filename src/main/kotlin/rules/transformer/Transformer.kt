package rules.transformer

import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
sealed interface Transformer : BaseRule {
    fun invoke(data: Array<IntArray>, parameter: Any): Array<BooleanArray>
}
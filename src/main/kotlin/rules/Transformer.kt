package rules

import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
sealed class Transformer : BaseRule() {
    abstract fun invoke(data: Array<IntArray>, parameter: Any): Array<BooleanArray>
}
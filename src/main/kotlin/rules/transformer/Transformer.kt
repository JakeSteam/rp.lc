package rules.transformer

import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
abstract class Transformer : BaseRule() {
    abstract fun invoke(data: Array<IntArray>, parameter: Any): Array<BooleanArray>
}
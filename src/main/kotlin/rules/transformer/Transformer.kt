package rules.transformer

import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
sealed class Transformer : BaseRule() {
    abstract fun invoke(data: Array<IntArray>, colour: Int): Array<BooleanArray>
}
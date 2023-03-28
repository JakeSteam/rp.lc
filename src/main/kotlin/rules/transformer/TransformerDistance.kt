package rules.transformer

import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
sealed class TransformerDistance : BaseRule() {
    abstract fun invoke(data: Array<IntArray>, colour: Int, distance: Int): Array<BooleanArray>
}
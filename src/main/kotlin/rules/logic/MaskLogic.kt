package rules.logic

import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
sealed class MaskLogic : BaseRule() {
    abstract fun invoke(data: Array<BooleanArray>, data2: Array<BooleanArray>): Array<BooleanArray>
}
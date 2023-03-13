package rules.transformer

import rules.BaseRule

sealed interface Transformer : BaseRule {
    fun invoke(data: Array<IntArray>, parameter: Any): Array<BooleanArray>
}
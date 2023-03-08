package generation.transformer

import generation.BaseRule

sealed interface Transformer : BaseRule {
    fun invoke(data: Array<IntArray>, parameter: Any): Array<BooleanArray>
}
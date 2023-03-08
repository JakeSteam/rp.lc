package pixel.transformer

import pixel.Rule

sealed interface Transformer : Rule {
    fun invoke(data: Array<IntArray>, parameter: Any): Array<BooleanArray>
}
package pixel.transformer

import pixel.Rule

sealed interface Transformer : Rule {
    fun transform(data: Array<IntArray>, parameter: Any): Array<BooleanArray>
}
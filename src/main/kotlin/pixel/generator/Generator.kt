package pixel.generator

import pixel.Rule

sealed interface Generator : Rule {
    fun invoke(input: Array<IntArray>) : Array<IntArray>
}
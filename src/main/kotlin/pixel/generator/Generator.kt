package pixel.generator

import pixel.Rule

sealed interface Generator : Rule {
    fun create(input: Array<IntArray>) : Array<IntArray>
}
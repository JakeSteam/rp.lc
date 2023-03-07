package pixel.generator

import pixel.Rule

sealed interface Generator : Rule {
    fun create(width: Int, height: Int) : Array<IntArray>
}
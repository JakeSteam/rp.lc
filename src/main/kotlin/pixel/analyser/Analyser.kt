package pixel.analyser

import pixel.Rule

sealed interface Analyser : Rule {
    fun invoke(data: Array<IntArray>): Int
}
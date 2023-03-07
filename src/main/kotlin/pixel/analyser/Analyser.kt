package pixel.analyser

import pixel.Rule

sealed interface Analyser : Rule {
    fun analyse(data: Array<IntArray>): Int
}
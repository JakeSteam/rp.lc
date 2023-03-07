package pixel.analyser

import pixel.Rule

sealed interface Analyser {
    fun analyse(data: Array<IntArray>): Int
}
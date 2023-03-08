package generation.analyser

import generation.BaseRule

sealed interface Analyser : BaseRule {
    fun invoke(data: Array<IntArray>): Int
}
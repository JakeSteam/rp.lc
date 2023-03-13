package rules.analyser

import rules.BaseRule

sealed interface Analyser : BaseRule {
    fun invoke(data: Array<IntArray>): Int
}
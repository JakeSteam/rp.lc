package rules.creator

import rules.BaseRule

sealed interface Creator : BaseRule {
    fun invoke(input: Array<IntArray>) : Array<IntArray>
}
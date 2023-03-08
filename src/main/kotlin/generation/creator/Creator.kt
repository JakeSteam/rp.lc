package generation.creator

import generation.BaseRule

sealed interface Creator : BaseRule {
    fun invoke(input: Array<IntArray>) : Array<IntArray>
}
package rules.creator

import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
sealed interface Creator : BaseRule {
    fun invoke(input: Array<IntArray>) : Array<IntArray>
}
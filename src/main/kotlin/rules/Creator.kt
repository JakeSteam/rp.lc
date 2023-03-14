package rules

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import rules.BaseRule

@Serializable
sealed class Creator : BaseRule() {
    abstract fun invoke(input: Array<IntArray>) : Array<IntArray>
}
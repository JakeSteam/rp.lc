package rules.creator

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import rules.BaseRule

@Serializable
abstract class Creator : BaseRule() {
    abstract fun invoke(input: Array<IntArray>) : Array<IntArray>
}
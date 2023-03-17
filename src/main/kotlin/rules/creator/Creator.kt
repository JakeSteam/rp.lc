package rules.creator

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import rules.BaseRule

@Serializable
sealed class Creator : BaseRule() {
    abstract fun invoke(width: Int, height: Int) : Array<IntArray>
}
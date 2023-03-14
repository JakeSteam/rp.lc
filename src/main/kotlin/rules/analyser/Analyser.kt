package rules.analyser

import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
abstract class Analyser : BaseRule() {
    abstract fun invoke(data: Array<IntArray>): Int
}
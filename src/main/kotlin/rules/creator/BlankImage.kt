package rules.creator

import kotlinx.serialization.Serializable

@Serializable
object BlankImage : Creator() {

    override fun invoke(input: Array<IntArray>) : Array<IntArray> {
        return Array(input.size) { IntArray(input[0].size) }
    }
}
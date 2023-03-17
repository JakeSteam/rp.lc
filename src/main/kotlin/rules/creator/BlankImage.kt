package rules.creator

import kotlinx.serialization.Serializable

@Serializable
object BlankImage : Creator() {

    override fun invoke(width: Int, height: Int) : Array<IntArray> {
        return Array(height) { IntArray(width) }
    }
}
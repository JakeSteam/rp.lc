package rules.transformer

import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
object ColourMatch : BaseRule() {
    fun invoke(data: Array<IntArray>, colour: Int): Array<BooleanArray> {
        val output = Array(data.size) { BooleanArray(data[0].size) }
        for (y in data.indices) {
            for (x in data[0].indices) {
                val pixel = data[y][x]
                output[y][x] = pixel == colour
            }
        }
        return output
    }
}
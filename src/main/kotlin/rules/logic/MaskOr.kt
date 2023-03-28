package rules.logic

import kotlinx.serialization.Serializable

@Serializable
object MaskOr : MaskLogic() {

    override fun invoke(data: Array<BooleanArray>, data2: Array<BooleanArray>): Array<BooleanArray> {
        val output = Array(data.size) { BooleanArray(data[0].size) }
        for (y in data.indices) {
            for (x in data[0].indices) {
                output[y][x] = data[y][x] || data2[y][x]
            }
        }
        return output
    }
}
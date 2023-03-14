package rules

object ColourMatch : Transformer() {
    override fun invoke(data: Array<IntArray>, parameter: Any): Array<BooleanArray> {
        val output = Array(data.size) { BooleanArray(data[0].size) }
        for (y in data.indices) {
            for (x in data[0].indices) {
                val pixel = data[y][x]
                output[y][x] = pixel == parameter
            }
        }
        return output
    }
}
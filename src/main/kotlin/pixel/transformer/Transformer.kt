package pixel.transformer

sealed interface Transformer {
    fun transform(data: Array<IntArray>, parameter: Any): Array<BooleanArray>
}
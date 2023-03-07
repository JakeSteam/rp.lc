package pixel.generator

sealed interface Generator {
    fun create(width: Int, height: Int) : Array<IntArray>
}
package pixel.analyser

sealed interface Analyser {
    fun analyse(data: Array<IntArray>): Int
}
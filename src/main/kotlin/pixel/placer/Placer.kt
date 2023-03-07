package pixel.placer

sealed interface Placer {

    fun place(image: Array<IntArray>, mask: Array<BooleanArray>, ifTrue: Int, ifFalse: Int): Array<IntArray>
}
package pixel.placer

import pixel.Rule

sealed interface Placer : Rule {

    fun place(image: Array<IntArray>, mask: Array<BooleanArray>, ifTrue: Int, ifFalse: Int): Array<IntArray>
}
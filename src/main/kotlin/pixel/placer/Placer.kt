package pixel.placer

import config.schema.Config
import pixel.Rule

sealed interface Placer : Rule {

    fun place(image: Array<IntArray>, mask: Array<BooleanArray>, ifTrue: Config.Tile, ifFalse: Config.Tile): Array<IntArray>
}
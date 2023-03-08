package generation.placer

import config.Config
import generation.BaseRule

sealed interface Placer : BaseRule {

    fun invoke(image: Array<IntArray>, mask: Array<BooleanArray>, ifTrue: Config.Tile, ifFalse: Config.Tile): Array<IntArray>
}
package rules.placer

import config.Config
import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
sealed interface Placer : BaseRule {
    fun invoke(image: Array<IntArray>, mask: Array<BooleanArray>, ifTrue: Config.Tile, ifFalse: Config.Tile): Array<IntArray>
}
package rules.placer

import config.Config
import kotlinx.serialization.Serializable
import rules.BaseRule

@Serializable
sealed class Placer : BaseRule() {
    abstract fun invoke(data: Array<IntArray>, mask: Array<BooleanArray>, ifTrue: Config.Tile, ifFalse: Config.Tile): Array<IntArray>
}
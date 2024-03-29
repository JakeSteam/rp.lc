package rules.placer

import config.Config
import kotlinx.serialization.Serializable

@Serializable
object ApplyMask : Placer() {
    override fun invoke(data: Array<IntArray>, mask: Array<BooleanArray>, ifTrue: Config.Tile, ifFalse: Config.Tile): Array<IntArray> {
        for (y in mask.indices) {
            for (x in mask[0].indices) {
                val shouldApply = mask[y][x]
                data[y][x] = if (shouldApply) ifTrue.colour else ifFalse.colour
            }
        }
        return data
    }
}
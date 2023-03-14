package rules.placer

import config.Config
import kotlinx.serialization.Serializable
import util.ImageFileUtil

@Serializable
object OutputImage : Placer() {
    override fun invoke(image: Array<IntArray>, mask: Array<BooleanArray>, ifTrue: Config.Tile, ifFalse: Config.Tile): Array<IntArray> {
        ImageFileUtil().save(image, "ahhh.png")
        return image
    }
}
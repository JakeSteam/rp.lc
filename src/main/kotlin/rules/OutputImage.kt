package rules

import config.Config
import image.ImageWriter
import kotlinx.serialization.Serializable

@Serializable
object OutputImage : Placer() {
    override fun invoke(image: Array<IntArray>, mask: Array<BooleanArray>, ifTrue: Config.Tile, ifFalse: Config.Tile): Array<IntArray> {
        ImageWriter().save(image, "ahhh.png")
        return image
    }
}
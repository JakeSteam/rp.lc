package rules.placer

import config.Config
import image.ImageWriter

object OutputImage : Placer {
    override fun invoke(image: Array<IntArray>, mask: Array<BooleanArray>, ifTrue: Config.Tile, ifFalse: Config.Tile): Array<IntArray> {
        ImageWriter().save(image, "ahhh.png")
        return image
    }
}
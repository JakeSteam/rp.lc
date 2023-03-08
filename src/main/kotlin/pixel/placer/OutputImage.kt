package pixel.placer

import config.schema.Config
import image.ImageWriter

object OutputImage : Placer {
    override fun invoke(image: Array<IntArray>, mask: Array<BooleanArray>, ifTrue: Config.Tile, ifFalse: Config.Tile): Array<IntArray> {
        ImageWriter().save(image, "ahhh")
        return image
    }
}
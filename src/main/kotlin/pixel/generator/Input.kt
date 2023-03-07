package pixel.generator

import image.ImageReader

object Input : Generator {

    override fun create() : Array<IntArray> {
        return ImageReader().loadImage()!!.bytes
    }
}
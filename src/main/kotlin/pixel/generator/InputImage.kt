package pixel.generator

import image.ImageReader

object InputImage : Generator {

    override fun create() : Array<IntArray> {
        return ImageReader().loadImage()!!.bytes
    }
}
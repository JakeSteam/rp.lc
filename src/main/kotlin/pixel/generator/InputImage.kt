package pixel.generator

import image.ImageReader

object InputImage : Generator {

    override fun create(input: Array<IntArray>) : Array<IntArray> {
        return ImageReader().loadImage()!!.bytes
    }
}
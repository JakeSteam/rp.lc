package rules.creator

import image.ImageReader

object InputImage : Creator {

    override fun invoke(input: Array<IntArray>) : Array<IntArray> {
        return ImageReader().loadImage()!!.bytes
    }
}
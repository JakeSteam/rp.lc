package rules

import image.ImageReader
import kotlinx.serialization.Serializable

@Serializable
object InputImage : Creator() {

    override fun invoke(input: Array<IntArray>) : Array<IntArray> {
        return ImageReader().loadImage()!!.bytes
    }
}
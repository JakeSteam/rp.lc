package rules.creator

import util.ImageFileUtil
import kotlinx.serialization.Serializable

@Serializable
object InputImage : Creator() {

    override fun invoke(input: Array<IntArray>) : Array<IntArray> {
        return ImageFileUtil().loadImage()!!.bytes
    }
}
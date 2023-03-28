package rules.creator

import kotlinx.serialization.Serializable
import util.ColourUtil

@Serializable
object CheckerboardImage : Creator() {

    override fun invoke(width: Int, height: Int) : Array<IntArray> {
        val black = ColourUtil.toColor("#000000")!!.rgb
        val white = ColourUtil.toColor("#FFFFFF")!!.rgb
        val evenRow = IntArray(width) {
            if (it % 2 == 0) black else white
        }
        val oddRow = IntArray(width) {
            if (it % 2 == 0) white else black
        }
        return Array(height) {
            if (it % 2 == 0) evenRow else oddRow
        }
    }
}
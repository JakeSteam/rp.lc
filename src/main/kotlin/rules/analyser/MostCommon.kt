package rules.analyser

import kotlinx.serialization.Serializable
import util.LoggingUtil

@Serializable
object MostCommonOuter : Analyser() {

    override fun invoke(data: Array<IntArray>): Int {
        LoggingUtil.d("Looking for most common outer pixel")
        val pixelColours: MutableMap<Int, Int> = HashMap()
        val heightPixels = data.size - 1
        val widthPixels = data[0].size - 1

        // Top & bottom row
        for (x in 0..widthPixels) {
            val topPixel = data[0][x]
            LoggingUtil.pixel(x, 0, topPixel)
            pixelColours.merge(topPixel, 1, Int::plus)

            val bottomPixel = data[heightPixels][x]
            LoggingUtil.pixel(x, heightPixels, bottomPixel)
            pixelColours.merge(bottomPixel, 1, Int::plus)
        }

        // Left & right column (excluding top & bottom pixels)
        for (y in 1 until heightPixels) {
            val leftPixel = data[y][0]
            LoggingUtil.pixel(0, y, leftPixel)
            pixelColours.merge(leftPixel, 1, Int::plus)

            val rightPixel = data[y][widthPixels]
            LoggingUtil.pixel(widthPixels, y, leftPixel)
            pixelColours.merge(rightPixel, 1, Int::plus)
        }

        return pixelColours.toList().maxByOrNull { it.second }!!.first
    }

}

@Serializable
object MostCommonInner : Analyser() {

    override fun invoke(data: Array<IntArray>): Int {
        LoggingUtil.d("Looking for most common inner pixel")
        val pixelColours: MutableMap<Int, Int> = HashMap()

        for (y in 0 until data.size - 1) {
            for (x in 0 until data[0].size - 1) {
                val pixel = data[y][x]
                LoggingUtil.pixel(x, y, pixel)
                pixelColours.merge(pixel, 1, Int::plus)
            }
        }

        return pixelColours.toList().maxByOrNull { it.second }!!.first
    }

}

@Serializable
object MostCommon : Analyser() {

    override fun invoke(data: Array<IntArray>): Int {
        LoggingUtil.d("Looking for most common pixel")
        val pixelColours: MutableMap<Int, Int> = HashMap()

        for (y in data.indices) {
            for (x in 0 until data[0].size) {
                val pixel = data[y][x]
                LoggingUtil.pixel(x, y, pixel)
                pixelColours.merge(pixel, 1, Int::plus)
            }
        }

        return pixelColours.toList().maxByOrNull { it.second }!!.first
    }

}
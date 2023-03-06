package pixel.read

import java.awt.image.BufferedImage

class MostCommon {

    enum class MostCommonFilter { Outer, Inner, All }

    fun find(inputData: BufferedImage, filter: MostCommonFilter): Int {
        return when (filter) {
            MostCommonFilter.Outer -> getMostCommonOuterPixel(inputData)
            MostCommonFilter.Inner -> getMostCommonInnerPixel(inputData)
            MostCommonFilter.All -> getMostCommonPixel(inputData)
        }
    }

    private fun getMostCommonOuterPixel(inputData: BufferedImage): Int {
        FetchLog.d("Looking for most common outer pixel")
        val pixelColours: MutableMap<Int, Int> = HashMap()
        val heightPixels = inputData.height - 1
        val widthPixels = inputData.width - 1

        // Top & bottom row
        for (x in 0..widthPixels) {
            val topPixel = inputData.getRGB(x, 0)
            FetchLog.pixel(x, 0, topPixel)
            pixelColours.merge(topPixel, 1, Int::plus)

            val bottomPixel = inputData.getRGB(x, heightPixels)
            FetchLog.pixel(x, heightPixels, bottomPixel)
            pixelColours.merge(bottomPixel, 1, Int::plus)
        }

        // Left & right column (excluding top & bottom pixels)
        for (y in 1 until heightPixels) {
            val leftPixel = inputData.getRGB(0, y)
            FetchLog.pixel(0, y, leftPixel)
            pixelColours.merge(leftPixel, 1, Int::plus)

            val rightPixel = inputData.getRGB(widthPixels, y)
            FetchLog.pixel(widthPixels, y, leftPixel)
            pixelColours.merge(rightPixel, 1, Int::plus)
        }

        return pixelColours.getTop()
    }

    private fun getMostCommonInnerPixel(inputData: BufferedImage): Int {
        FetchLog.d("Looking for most common inner pixel")
        val pixelColours: MutableMap<Int, Int> = HashMap()

        for (y in 0 until inputData.height - 1) {
            for (x in 0 until inputData.width - 1) {
                val pixel = inputData.getRGB(x, y)
                FetchLog.pixel(x, y, pixel)
                pixelColours.merge(pixel, 1, Int::plus)
            }
        }

        return pixelColours.getTop()
    }

    private fun getMostCommonPixel(inputData: BufferedImage): Int {
        FetchLog.d("Looking for most common pixel")
        val pixelColours: MutableMap<Int, Int> = HashMap()

        for (y in 0 until inputData.height) {
            for (x in 0 until inputData.width) {
                val pixel = inputData.getRGB(x, y)
                FetchLog.pixel(x, y, pixel)
                pixelColours.merge(pixel, 1, Int::plus)
            }
        }

        return pixelColours.getTop()
    }

    private fun MutableMap<Int, Int>.getTop(): Int {
        return this.toList().maxByOrNull { it.second }!!.first
    }
}
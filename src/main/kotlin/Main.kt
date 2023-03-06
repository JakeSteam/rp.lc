import file.ImageReader
import file.ImageWriter
import pixel.read.MostCommon
import java.awt.Color
import java.awt.image.BufferedImage

fun main(args: Array<String>) {
    val inputData = ImageReader().loadImage()
    if (inputData == null || inputData.bytes.size < 10 || inputData.bytes[1].size < 10) {
        return
    }

    val outputData = convertImage(inputData.bytes)

    ImageWriter().save(outputData, inputData.filename)
}

fun convertImage(bytes: Array<IntArray>): Array<IntArray> {
    val outerPixelColor = MostCommon().find(bytes, MostCommon.MostCommonFilter.Outer)
    val water = Color(0, 0, 255, 255).rgb
    val land = Color(0, 92, 0, 255).rgb

    val output = Array(bytes.size) { IntArray(bytes[0].size) }

    // Need to apply all logic to pixel map here
    // Then can write entire array to file
    for (y in bytes.indices) {
        for (x in bytes[0].indices) {
            val pixel = bytes[y][x]
            val colour = if (pixel == outerPixelColor) water else land
            output[y][x] = colour
        }
    }

    return output
}
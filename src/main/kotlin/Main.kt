import image.ImageReader
import image.ImageWriter
import pixel.colour.HexReader
import pixel.read.MostCommon

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

    val water = HexReader.toColor("#3383FF")!!.rgb
    val land = HexReader.toColor("#10A949")!!.rgb

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
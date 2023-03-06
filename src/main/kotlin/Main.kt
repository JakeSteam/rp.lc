import file.ImageReader
import file.ImageWriter
import pixel.read.MostCommon
import java.awt.Color
import java.awt.image.BufferedImage

fun main(args: Array<String>) {
    val inputData = ImageReader().loadImage()
    if (inputData.image == null || inputData.filename == null
        || inputData.image.width < 10 || inputData.image.height < 10) return

    val outputData = convertImage(inputData.image)

    ImageWriter().save(outputData, inputData.filename)
}

fun convertImage(inputData: BufferedImage): BufferedImage {
    val outerPixelColor = MostCommon().find(inputData, MostCommon.MostCommonFilter.Outer)
    val water = Color(0, 0, 255, 255).rgb
    val land = Color(0, 92, 0, 255).rgb

    // Need to apply all logic to pixel map here
    // Then can write entire array to file
    for (y in 0 until inputData.height) {
        for (x in 0 until inputData.width) {
            //println("Writing to $x, $y")
            val pixel = inputData.getRGB(x, y)
            val colour = if (pixel == outerPixelColor) water else land
            inputData.setRGB(x, y, colour)
        }
    }

    return inputData
}
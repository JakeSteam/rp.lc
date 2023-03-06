import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Paths
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val projectDir = Paths.get("").toAbsolutePath().toString()
    val inputDir = "/input/"
    val outputDir = "/output/"

    val inputPath = locateImage(projectDir, inputDir) ?: return
    val inputData = loadImage(inputPath)
    if (inputData.width < 10 || inputData.height < 10) return

    val outputData = convertImage(inputData)
    saveOutput(projectDir, outputDir, inputPath.name, outputData)
}

fun locateImage(projectDir: String, inputDir: String): File? {
    val inputPath = File(projectDir + inputDir)
    return inputPath.listFiles()?.firstOrNull()
}

fun loadImage(inputFile: File): BufferedImage {
    return ImageIO.read(inputFile)
}

fun convertImage(inputData: BufferedImage): BufferedImage {
    val outerPixelColor = getModeOuterPixel(inputData).rgb
    val water = Color(0, 0, 255, 255).rgb
    val land = Color(255, 0, 0, 255).rgb

    for (y in 0..inputData.height) {
        for (x in 0..inputData.width) {
            val pixel = inputData.getRGB(x, y)
            val colour = if (pixel == outerPixelColor) water else land
            inputData.setRGB(x, y, colour)
        }
    }

    return inputData
}

fun getModeOuterPixel(inputData: BufferedImage): Color {
    val pixelColours : MutableMap<Int, Int> = HashMap()

    // Top & bottom row
    for (x in 0..inputData.width) {
        val topPixel = inputData.getRGB(x, 0)
        pixelColours.merge(topPixel, 1, Int::plus)

        val bottomPixel = inputData.getRGB(x, inputData.height)
        pixelColours.merge(bottomPixel, 1, Int::plus)
    }

    // Left & right column (excluding top & bottom pixels
    for (y in 1 until inputData.height) {
        val leftPixel = inputData.getRGB(0, y)
        pixelColours.merge(leftPixel, 1, Int::plus)

        val rightPixel = inputData.getRGB(inputData.width, y)
        pixelColours.merge(rightPixel, 1, Int::plus)
    }


    return Color(pixelColours.toList()
        .maxByOrNull { it.second }
        !!.second)
}

fun saveOutput(projectDir: String, outputDir: String, outputName: String, outputData: BufferedImage) {
    val outputFile = File(projectDir + outputDir, outputName)
    ImageIO.write(outputData, "png", outputFile)
}

data class Pixel(val red: Int, val green: Int, val blue: Int, val alpha: Int)
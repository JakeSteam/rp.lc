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
    println("Loaded image ${inputPath.name}. Width ${inputData.width}, height: ${inputData.height}")
    if (inputData.width < 10 || inputData.height < 10) return

    val outputData = convertImage(inputData)
    saveOutput(projectDir, outputDir, inputPath.name, outputData)
}

fun locateImage(projectDir: String, inputDir: String): File? {
    val inputPath = File(projectDir + inputDir)
    return inputPath.listFiles()?.firstOrNull { file -> file.isFile && file.extension == "png" }
}

fun loadImage(inputFile: File): BufferedImage {
    return ImageIO.read(inputFile)
}

fun convertImage(inputData: BufferedImage): BufferedImage {
    val outerPixelColor = getModeOuterPixel(inputData)
    val water = Color(0, 0, 255, 255).rgb
    val land = Color(0, 92, 0, 255).rgb

    //println("Writing water & land values")
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

fun getModeOuterPixel(inputData: BufferedImage): Int {
    val pixelColours : MutableMap<Int, Int> = HashMap()
    println("Looking for mode outer pixel")

    // Top & bottom row
    for (x in 0 until inputData.width) {
        val topPixel = inputData.getRGB(x, 0)
        println("Checked x: $x, y: 0, colour is $topPixel")
        pixelColours.merge(topPixel, 1, Int::plus)

        val bottomPixel = inputData.getRGB(x, inputData.height - 1)
        println("Checked x: $x, y: ${inputData.height - 1}, colour is $bottomPixel")
        pixelColours.merge(bottomPixel, 1, Int::plus)
    }

    // Left & right column (excluding top & bottom pixels
    for (y in 1 until inputData.height - 1) {
        println("Checking x: 0, y: $y")
        val leftPixel = inputData.getRGB(0, y)
        pixelColours.merge(leftPixel, 1, Int::plus)

        println("Checking x: ${inputData.width - 1}, y: $y")
        val rightPixel = inputData.getRGB(inputData.width - 1, y)
        pixelColours.merge(rightPixel, 1, Int::plus)
    }

    return pixelColours.toList().maxByOrNull { it.second }!!.first
}

fun saveOutput(projectDir: String, outputDir: String, outputName: String, outputData: BufferedImage) {
    val outputFile = File(projectDir + outputDir, outputName)
    ImageIO.write(outputData, "png", outputFile)
}
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val inputDir = "/input/"
    val outputDir = "/output/"

    val inputPath = locateImage(inputDir) ?: return
    val inputData = loadImage(inputPath)
    val outputData = convertImage(inputData)
    saveOutput(outputDir, inputPath.name, outputData)
}

fun locateImage(inputDir: String): File? {
    return File(inputDir).listFiles()?.firstOrNull()
}

fun loadImage(inputFile: File): BufferedImage {
    return ImageIO.read(inputFile)
}

fun convertImage(inputData: BufferedImage): BufferedImage {
    val topLeft = inputData.getRGB(0, 0)
    inputData.setRGB(0, 0, topLeft + 100)
    return inputData
}

fun saveOutput(outputDir: String, outputName: String, outputData: BufferedImage) {
    val outputFile = File(outputDir, outputName)
    ImageIO.write(outputData, "png", outputFile)
}
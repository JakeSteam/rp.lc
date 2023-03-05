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
    val topLeft = inputData.getRGB(0, 0)
    inputData.setRGB(0, 0, topLeft + 100)
    return inputData
}

fun saveOutput(projectDir: String, outputDir: String, outputName: String, outputData: BufferedImage) {
    val outputFile = File(projectDir + outputDir, outputName)
    ImageIO.write(outputData, "png", outputFile)
}
package file

import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Paths
import javax.imageio.ImageIO

class ImageReader {

    data class ImageReaderResult(val image: BufferedImage?, val filename: String?)

    fun loadImage(): ImageReaderResult {
        val inputDir = getInputDir()
        val validFile = getFirstValidFile(inputDir)
        return ImageReaderResult(ImageIO.read(validFile), validFile?.name)
    }

    private fun getInputDir(): File {
        val projectDir = Paths.get("").toAbsolutePath().toString()
        val inputDir = "/input/"
        return File(projectDir + inputDir)
    }

    private fun getFirstValidFile(directory: File): File? {
        if (!directory.isDirectory) return null

        return directory.listFiles()
            ?.firstOrNull { file ->
                file.isFile && file.extension == "png"
            }
    }

}
package file

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import javax.imageio.ImageIO

class ImageWriter {

    fun save(data: BufferedImage, filename: String): Boolean {
        val output = getOutputPath(filename)
        return try {
            ImageIO.write(data, "png", output)
        } catch (e: IOException) {
            false
        }
    }

    private fun getOutputPath(filename: String): File {
        val projectDir = Paths.get("").toAbsolutePath().toString()
        val inputDir = "/output/"
        return File(projectDir + inputDir, filename)
    }

}
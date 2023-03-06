package file

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import javax.imageio.ImageIO

class ImageWriter {

    fun save(bytes: Array<IntArray>, filename: String): Boolean {
        val output = getOutputPath(filename)

        val image = BufferedImage(bytes[0].size, bytes.size, BufferedImage.TYPE_INT_ARGB)
        // What does .setRGB area do?
        for (y in bytes.indices) {
            for (x in bytes[0].indices) {
                image.setRGB(x, y, bytes[y][x])
                FileLog.pixel(x, y, bytes[y][x])
            }
        }

        return try {
            ImageIO.write(image, "png", output)
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
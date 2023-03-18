package util

import image.ImageLog
import java.awt.Image
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.awt.image.PixelGrabber
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import javax.imageio.ImageIO


class ImageFileUtil {

    data class ImageReaderResult(val bytes: Array<IntArray>, val filename: String)

    fun loadImage(width: Int, height: Int): ImageReaderResult? {
        val inputDir = getInputDir()
        val validFile = getFirstValidFile(inputDir) ?: return null
        val image = ImageIO.read(validFile)
        val scaledImage = resize(image, height, width)!!
        val bytes = getRGBPixels(scaledImage)!! //imageToArray(image)
        return ImageReaderResult(bytes, validFile.nameWithoutExtension)
    }

    private fun getInputDir(): File {
        val projectDir = Paths.get("").toAbsolutePath().toString()
        val inputDir = "/input/"
        return File(projectDir + inputDir)
    }

    private fun getOutputPath(filename: String): File {
        val projectDir = Paths.get("").toAbsolutePath().toString()
        val inputDir = "/output/"
        return File(projectDir + inputDir, filename)
    }

    private fun getFirstValidFile(directory: File): File? {
        if (!directory.isDirectory) return null

        val supportedExtensions = arrayOf("png", "bmp", "jpg", "jpeg", "gif")
        return directory.listFiles()
            ?.firstOrNull { file ->
                file.canRead() && supportedExtensions.contains(file.extension)
            }
    }

    private fun getRGBPixels(img: BufferedImage): Array<IntArray> {
        val result: Array<IntArray>
        return try {
            val g = PixelGrabber(img, 0, 0, -1, -1, true)
            g.grabPixels()
            val pixels = g.pixels as IntArray
            val w = g.width
            val h = g.height
            result = Array(w) { IntArray(h) }
            var j = 0
            var count = 0
            while (j < h) {
                for (i in 0 until w) result[i][j] = pixels[count++]
                j++
            }
            result
        } catch (ex: Exception) {
            ex.printStackTrace()
            emptyArray()
        }
    }

    private fun resize(img: BufferedImage, height: Int, width: Int): BufferedImage? {
        val tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH)
        val resized = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val g2d = resized.createGraphics()
        g2d.drawImage(tmp, 0, 0, null)
        g2d.dispose()
        return resized
    }

    fun save(bytes: Array<IntArray>, filename: String): Boolean {
        val output = getOutputPath("$filename.png")
        output.mkdirs()

        val image = BufferedImage(bytes[0].size, bytes.size, BufferedImage.TYPE_INT_ARGB)
        for (y in bytes.indices) {
            for (x in bytes[0].indices) {
                image.setRGB(x, y, bytes[y][x])
                ImageLog.pixel(x, y, bytes[y][x])
            }
        }

        return try {
            ImageIO.write(image, "png", output)
        } catch (e: IOException) {
            false
        }
    }

}
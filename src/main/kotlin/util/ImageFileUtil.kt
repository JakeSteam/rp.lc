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

    // http://www.java2s.com/example/java/2d-graphics/gets-the-bufferedimage-as-a-2d-array-of-rgb-pixel-values.html
    private fun getRGBPixels(img: BufferedImage): Array<IntArray> {
        val result: Array<IntArray>
        return try {
            val grabber = PixelGrabber(img, 0, 0, -1, -1, true)
            grabber.grabPixels()
            val pixels = grabber.pixels as IntArray
            result = Array(grabber.height) { IntArray(grabber.width) }
            var count = 0
            for (y in 0 until grabber.height) {
                for (x in 0 until grabber.width) {
                    result[y][x] = pixels[count++]
                }
            }
            result
        } catch (ex: Exception) {
            ex.printStackTrace()
            emptyArray()
        }
    }

    // https://memorynotfound.com/java-resize-image-fixed-width-height-example/
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
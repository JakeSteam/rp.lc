package util

import image.ImageLog
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import javax.imageio.ImageIO


class ImageFileUtil {

    data class ImageReaderResult(val bytes: Array<IntArray>, val filename: String)

    fun loadImage(): ImageReaderResult? {
        val inputDir = getInputDir()
        val validFile = getFirstValidFile(inputDir) ?: return null
        val image = ImageIO.read(validFile)
        //val scaledImage = resizeImage(image, 100, 100)
        val bytes = imageToArray(image)
        return ImageReaderResult(bytes, validFile.name)
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

        return directory.listFiles()
            ?.firstOrNull { file ->
                file.isFile && file.extension == "png"
            }
    }

    // https://stackoverflow.com/a/9470843/608312
    // e.g. bytes[1][5] = 1st row, 5th col = each array is a row
    // So a loop will be row by row, left to right!
    private fun imageToArray(image: BufferedImage): Array<IntArray> {
        val pixels = (image.raster.dataBuffer as DataBufferByte).data
        val width = image.width
        val height = image.height
        val hasAlphaChannel = image.alphaRaster != null
        val result = Array(height) { IntArray(width) }
        if (hasAlphaChannel) {
            val pixelLength = 4
            var pixel = 0
            var row = 0
            var col = 0
            while (pixel + 3 < pixels.size) {
                var argb = 0
                argb += pixels[pixel].toInt() and 0xff shl 24 // alpha
                argb += pixels[pixel + 1].toInt() and 0xff // blue
                argb += pixels[pixel + 2].toInt() and 0xff shl 8 // green
                argb += pixels[pixel + 3].toInt() and 0xff shl 16 // red
                result[row][col] = argb
                col++
                if (col == width) {
                    col = 0
                    row++
                }
                pixel += pixelLength
            }
        } else {
            val pixelLength = 3
            var pixel = 0
            var row = 0
            var col = 0
            while (pixel + 2 < pixels.size) {
                var argb = 0
                argb += -16777216 // 255 alpha
                argb += pixels[pixel].toInt() and 0xff // blue
                argb += pixels[pixel + 1].toInt() and 0xff shl 8 // green
                argb += pixels[pixel + 2].toInt() and 0xff shl 16 // red
                result[row][col] = argb
                col++
                if (col == width) {
                    col = 0
                    row++
                }
                pixel += pixelLength
            }
        }
        return result
    }

    fun save(bytes: Array<IntArray>, filename: String): Boolean {
        val output = getOutputPath(filename)
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
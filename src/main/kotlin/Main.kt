import image.ImageReader
import image.ImageWriter
import pixel.util.HexReader
import pixel.analyser.MostCommonOuter
import pixel.generator.Blank
import pixel.placer.ApplyMask
import pixel.transformer.ColourMatch

fun main(args: Array<String>) {
    val inputData = ImageReader().loadImage()
    if (inputData == null || inputData.bytes.size < 10 || inputData.bytes[1].size < 10) {
        return
    }

    // Tile definitions
    val water = HexReader.toColor("#3383FF")!!.rgb
    val land = HexReader.toColor("#10A949")!!.rgb

    // Take image as input, apply analyser `mostcommon`, get colour out
    val outerPixel = MostCommonOuter.analyse(inputData.bytes)

    // Take image AND colour as input, apply transformer `colourmatches`, get boolean matrix out
    val matchingPixels = ColourMatch.transform(inputData.bytes, outerPixel)

    // Take image metada as input, apply generator `blank`, get empty image out
    val outputImage = Blank.create(inputData.bytes.size, inputData.bytes[0].size)

    // Take true / false matrix AND true tile AND false tile as input, apply placer `setmatching`
    val output = ApplyMask.place(outputImage, matchingPixels, water, land)

    ImageWriter().save(output, inputData.filename)
}
import config.schema.Config
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

    val testConfig = Config(
        meta = Config.Meta(
            name = "Test config",
            author = "Jake Lee",
            authorUrl = "https://jakelee.co.uk",
            engineVersion = "0.0.1"
        ),
        tiles = listOf(
            Config.Tile("Water", "Used to swim in", HexReader.toColor("#3383FF")!!.rgb),
            Config.Tile("Land", "Used to walk on", HexReader.toColor("#10A949")!!.rgb)
        ),
        rules = listOf(
            // All rules need a base to be a list of
            // Then some kind of definition of "rule", input, and output...

            /*
            Config defines generation rules.
            Generation rules take an input and an output, what are their types? For example:
            - Take image as input, apply analyser `mostcommon`, get colour out
            - Take image AND colour as input, apply transformer `colourmatches`, get black / white matrix out
            - Take true / false matrix AND true tile AND false tile as input, apply placer `booleanplacer`
            - Take coloured image as input, apply placer `save`

            Input / output types: "Image", "Colour", "Tile"

            Notes:
            - No artificial priority is needed, the order can be defined based on references
            - Whilst referencing each other will be tricky, it can be solved with arbitrary IDs
            - Analyser / Transformer / Placer should each extend a class, then each be extended by things like MostCommon
            - HOW can a rule define what it needed? For example mostcommon just needs image... how on earth to map from JSON
             */
        )
    )

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
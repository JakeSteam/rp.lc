import config.schema.Config
import config.util.getInputParams
import config.util.getReturnType
import config.validator.RuleValidator
import image.ImageReader
import pixel.analyser.MostCommonOuter
import pixel.generator.Blank
import pixel.generator.Input
import pixel.placer.ApplyMask
import pixel.transformer.ColourMatch
import pixel.util.HexReader
import kotlin.reflect.full.valueParameters

fun main(args: Array<String>) {
    val inputData = ImageReader().loadImage()
    if (inputData == null || inputData.bytes.size < 10 || inputData.bytes[1].size < 10) {
        return
    }

    // Special vars
    val input = inputData.bytes
    val width = inputData.bytes[0].size
    val height = inputData.bytes.size

    // Converted into object
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
            Config.GenerationRule(
                Input, "input", emptyList()
            ),
            Config.GenerationRule(
                MostCommonOuter, "outerPixel", listOf("input"),
            ),
            Config.GenerationRule(
                ColourMatch, "matchingPixels", listOf("input", "outerPixel")
            ),
            Config.GenerationRule(
                Blank, "outputImage", emptyList() // Hardcoded width & height!
            ),
            Config.GenerationRule(
                ApplyMask, "output", listOf("outputImage", "matchingPixels", "Water", "Land")
            )
        )
    )

    // Validator
    // Next steps:
    //  Extract validator somewhere useful
    //  Begin working on actioner (that runs validator first)
    //  Extract that
    //  Parse JSON
    //  Set up end to end proof of concept, update readme, sort logging etc
    RuleValidator().identifyConfigErrors(testConfig)?.let {
        print("Uh oh: $it")
    }

    /*
    // Tile definitions
    val water = HexReader.toColor("#3383FF")!!.rgb
    val land = HexReader.toColor("#10A949")!!.rgb

    // Take image as input, apply analyser `mostcommon`, get colour out
    val outerPixel = MostCommonOuter.analyse(inputData.bytes)

    // Take image AND colour as input, apply transformer `colourmatches`, get boolean matrix out
    val matchingPixels = ColourMatch.transform(inputData.bytes, outerPixel)

    // Take image metadata as input, apply generator `blank`, get empty image out
    val outputImage = Blank.create(inputData.bytes.size, inputData.bytes[0].size)

    // Take true / false matrix AND true tile AND false tile as input, apply placer `setmatching`
    val output = ApplyMask.place(outputImage, matchingPixels, water, land)

    ImageWriter().save(output, inputData.filename)
     */
}
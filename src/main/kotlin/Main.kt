import config.GenerationRuleActioner
import config.Config
import config.RuleValidator
import image.ImageReader
import rules.analyser.MostCommonOuter
import rules.creator.BlankImage
import rules.creator.InputImage
import rules.placer.ApplyMask
import rules.placer.OutputImage
import rules.transformer.ColourMatch
import util.HexReader

fun main(args: Array<String>) {
    val inputData = ImageReader().loadImage()
    if (inputData == null || inputData.bytes.size < 10 || inputData.bytes[1].size < 10) {
        return
    }

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
                InputImage, "input", arrayListOf()
            ),
            Config.GenerationRule(
                MostCommonOuter, "outerPixelColour", arrayListOf("input"),
            ),
            Config.GenerationRule(
                ColourMatch, "matchingPixels", arrayListOf("input", "outerPixelColour")
            ),
            Config.GenerationRule(
                BlankImage, "outputImage", arrayListOf("input")
            ),
            Config.GenerationRule(
                ApplyMask, "output", arrayListOf("outputImage", "matchingPixels", "Water", "Land")
            ),
            Config.GenerationRule(
                OutputImage, "finalOutput", arrayListOf("output")
            )
        )
    )

    // Validator
    // Next steps:
    //  x Extract validator somewhere useful
    //  x Begin working on actioner (that runs validator first)
    //  Extract that (fix filename hardcoding)
    //  Parse JSON
    //  Set up end to end proof of concept, update readme, sort logging etc
    RuleValidator().identifyConfigErrors(testConfig)?.let {
        println("Uh oh: $it")
        return
    }

    GenerationRuleActioner().performGenerationRules(testConfig.rules, testConfig.tiles)
}
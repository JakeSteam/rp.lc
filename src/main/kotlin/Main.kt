import config.GenerationRuleActioner
import config.Config
import config.ConfigReader
import config.RuleValidator
import image.ImageReader
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import rules.*
import rules.analyser.MostCommonOuter
import rules.creator.BlankImage
import rules.creator.Creator
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

    //ConfigReader().saveConfig(testConfig)

    val config = ConfigReader().loadConfig() ?: return

    RuleValidator().identifyConfigErrors(config)?.let {
        println("Uh oh: $it")
        return
    }

    GenerationRuleActioner().performGenerationRules(config.rules, config.tiles)
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
        Config.GenerationRule(
            InputImage, "input", arrayListOf()
        ),
        Config.GenerationRule(
            BlankImage, "outputImage", arrayListOf("input")
        ),
        Config.GenerationRule(
            MostCommonOuter, "outerPixelColour", arrayListOf("input")
        ),
        Config.GenerationRule(
            ColourMatch, "matchingPixels", arrayListOf("input", "outerPixelColour")
        ),
        Config.GenerationRule(
            ApplyMask, "output", arrayListOf("outputImage", "matchingPixels", "Water", "Land")
        ),
        Config.GenerationRule(
            OutputImage, "finalOutput", arrayListOf("output")
        )
    )
)
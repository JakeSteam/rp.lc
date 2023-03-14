import config.GenerationRuleActioner
import config.Config
import config.RuleValidator
import image.ImageReader
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import rules.*
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

    val jsonSerializer = Json {
        serializersModule = SerializersModule {
            polymorphic(BaseRule::class, BlankImage::class, BlankImage.serializer())
            polymorphic(BaseRule::class, InputImage::class, InputImage.serializer())
            polymorphic(BaseRule::class, ColourMatch::class, ColourMatch.serializer())
            polymorphic(BaseRule::class, MostCommonOuter::class, MostCommonOuter.serializer())
            polymorphic(BaseRule::class, ApplyMask::class, ApplyMask.serializer())
            polymorphic(BaseRule::class, OutputImage::class, OutputImage.serializer())
        }
    }
    val json = jsonSerializer.encodeToString(testConfig)
    println(json)
    val config = jsonSerializer.decodeFromString(Config.serializer(), json)

    // Validator
    // Next steps:
    //  x Extract validator somewhere useful
    //  x Begin working on actioner (that runs validator first)
    //  Extract that (fix filename hardcoding)
    //  Parse JSON
    //  Set up end to end proof of concept, update readme, sort logging etc
    RuleValidator().identifyConfigErrors(config)?.let {
        println("Uh oh: $it")
        return
    }

    GenerationRuleActioner().performGenerationRules(config.rules, config.tiles)
}
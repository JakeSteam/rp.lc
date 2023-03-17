import config.GenerationRuleActioner
import config.Config
import config.RuleValidator
import rules.analyser.MostCommonOuter
import rules.creator.BlankImage
import rules.placer.ApplyMask
import rules.transformer.ColourMatch
import util.ColourUtil
import util.TileUtil

fun main(args: Array<String>) {

    //ConfigFileUtil().saveConfig(testConfig)

    val config = testConfig //ConfigFileUtil().loadConfig() ?: return

    RuleValidator().identifyConfigErrors(config)?.let {
        println("Uh oh: $it")
        return
    }

    val engine = GenerationRuleActioner()
    engine.prepareInput()
    val output = engine.performGenerationRules(config.rules, config.tiles)
    val tiles = TileUtil.coloursToTiles(config.tiles, output)
    engine.prepareOutput(output)

    /*
    TODO:
    - How can each rule have access to metadata?
    - Instead of 1 big input object, there is "inputImage", "inputWidth" etc
    - Then config rules can easily use them, or use any other output
    - Don't forget to remove `= input.image`!
     */
}

val testConfig = Config(
    meta = Config.Meta(
        name = "Test config",
        author = "Jake Lee",
        authorUrl = "https://jakelee.co.uk",
        engineVersion = "0.0.1"
    ),
    tiles = listOf(
        Config.Tile("Water", "Used to swim in", ColourUtil.toColor("#3383FF")!!.rgb, listOf(
            Config.Tile.ResourceChange("Water", 1),
        )),
        Config.Tile("Land", "Used to walk on", ColourUtil.toColor("#10A949")!!.rgb, listOf(
            Config.Tile.ResourceChange("Grass", 5)
        ))
    ),
    resources = listOf(
        Config.Resource("Water", "It's wet", ColourUtil.toColor("#3383FF")!!.rgb, -1),
        Config.Resource("Grass", "It's green", ColourUtil.toColor("#10A949")!!.rgb, 1)
    ),
    rules = listOf(
        Config.GenerationRule(
            BlankImage, "outputImage", arrayListOf("inputWidth", "inputHeight")
        ),
        Config.GenerationRule(
            MostCommonOuter, "outerPixelColour", arrayListOf("inputImage")
        ),
        Config.GenerationRule(
            ColourMatch, "matchingPixels", arrayListOf("inputImage", "outerPixelColour")
        ),
        Config.GenerationRule(
            ApplyMask, "output", arrayListOf("outputImage", "matchingPixels", "Water", "Land")
        )
    )
)
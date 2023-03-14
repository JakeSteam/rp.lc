import config.GenerationRuleActioner
import config.Config
import config.RuleValidator
import rules.analyser.MostCommonOuter
import rules.creator.BlankImage
import rules.placer.ApplyMask
import rules.transformer.ColourMatch
import util.ColourUtil

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
    engine.prepareOutput(output)
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
        )
    )
)
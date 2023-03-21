import config.GenerationRuleActioner
import config.Config
import config.RuleValidator
import resources.ResourceCalculator
import rules.analyser.MostCommonOuter
import rules.creator.BlankImage
import rules.placer.ApplyMask
import rules.transformer.ColourMatch
import util.ColourUtil
import resources.TileMapper

fun main(args: Array<String>) {

    //ConfigFileUtil().saveConfig(testConfig)

    val config = testConfig //ConfigFileUtil().loadConfig() ?: return

    RuleValidator().identifyConfigErrors(config)?.let {
        println("Uh oh: $it")
        return
    }

    val engine = GenerationRuleActioner()
    engine.prepareInput(config.gameConfig.outputWidth, config.gameConfig.outputHeight)
    val output = engine.performGenerationRules(config.rules, config.tiles)
    val tiles = TileMapper.coloursToTiles(config.tiles, output)
    val resources = ResourceCalculator.tilesToResourceChanges(tiles, config.resources) // Totals are wrong?
    val score = resources.entries.sumOf { it.key.scoreImpact * it.value }
    val rank = config.gameConfig.requiredScorePerRank
        .sortedBy { it.first }
        .findLast { it.first < score }?.second
        ?: "No rank"
    engine.prepareOutput(output)

    println("$rank: $score")
}

val testConfig = Config(
    meta = Config.Meta(
        name = "Test config",
        author = "Jake Lee",
        authorUrl = "https://jakelee.co.uk",
        engineVersion = "0.0.1"
    ),
    gameConfig = Config.GameConfig(
        requiredScorePerRank = listOf(
            Pair(-50000, "Very Bad"),
            Pair(-10000, "Bad"),
            Pair(0, "OK"),
            Pair(10000, "Good"),
            Pair(50000, "Very Good"),
        ),
        outputWidth = 50,
        outputHeight = 10
    ),
    tiles = listOf(
        Config.Tile("Water", "Used to swim in", ColourUtil.toColor("#3383FF")!!.rgb, listOf(
            Config.Tile.ResourceChange("Water", 1),
        )),
        Config.Tile("Land", "Used to walk on", ColourUtil.toColor("#10A949")!!.rgb, listOf(
            Config.Tile.ResourceChange("Grass", 1)
        ))
    ),
    resources = listOf(
        Config.Resource("Water", "It's wet", ColourUtil.toColor("#3383FF")!!.rgb, -1),
        Config.Resource("Grass", "It's green", ColourUtil.toColor("#10A949")!!.rgb, 1)
    ),
    rules = listOf(
        Config.GenerationRule(
            BlankImage, "outputImage", mapOf(
                "width" to "inputWidth",
                "height" to "inputHeight"
            )
        ),
        Config.GenerationRule(
            MostCommonOuter, "outerPixelColour", mapOf(
                "data" to "inputImage"
            )
        ),
        Config.GenerationRule(
            ColourMatch, "matchingPixels", mapOf(
                "data" to "inputImage",
                "colour" to "-12820063"
            )
        ),
        Config.GenerationRule(
            ApplyMask, "output", mapOf(
                "data" to "outputImage",
                "mask" to "matchingPixels",
                "ifTrue" to "Water",
                "ifFalse" to "Land"
            )
        )
    )
)
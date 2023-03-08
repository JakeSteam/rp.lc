import config.schema.Config
import config.validator.RuleValidator
import image.ImageReader
import pixel.analyser.MostCommonOuter
import pixel.generator.BlankImage
import pixel.generator.InputImage
import pixel.placer.ApplyMask
import pixel.placer.OutputImage
import pixel.transformer.ColourMatch
import pixel.util.HexReader

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
                InputImage, "input", emptyList()
            ),
            Config.GenerationRule(
                MostCommonOuter, "outerPixelColour", listOf("input"),
            ),
            Config.GenerationRule(
                ColourMatch, "matchingPixels", listOf("input", "outerPixelColour")
            ),
            Config.GenerationRule(
                BlankImage, "outputImage", listOf("input")
            ),
            Config.GenerationRule(
                ApplyMask, "output", listOf("outputImage", "matchingPixels", "Water", "Land")
            ),
            Config.GenerationRule(
                OutputImage, "finalOutput", listOf("output")
            )
        )
    )

    // Validator
    // Next steps:
    //  x Extract validator somewhere useful
    //  Begin working on actioner (that runs validator first)
    //  Extract that
    //  Parse JSON
    //  Set up end to end proof of concept, update readme, sort logging etc
    RuleValidator().identifyConfigErrors(testConfig)?.let {
        print("Uh oh: $it")
    }

    // Actioner
    val outputNode = testConfig.rules.first { it.rule == OutputImage}
    val solvedNodes = hashMapOf<String, Any>()

    // Whilst we haven't solved the final node, keep trying
    while (!solvedNodes.contains(outputNode.outputId)) {
        // Get all the nodes that haven't been solved yet
        val targetNodes = testConfig.rules.filter { !solvedNodes.containsKey(it.outputId) }
        targetNodes.forEach { generationRule ->
            // For each one, if all needed inputs have been obtained, invoke it
            if (solvedNodes.keys.containsAll(generationRule.inputIds)) {
                // Pull the outputs we need
                val relevantSolvedNodes = solvedNodes.filterKeys { generationRule.inputIds.contains(it) }

                // Invoke function with the retrieved outputs
                val nodeOutput = generationRule.rule::class.members.find { it.name == "invoke" }!!.call(
                    *relevantSolvedNodes.values.toTypedArray()
                )

                // Add the result into solved nodes, for future use
                solvedNodes[generationRule.outputId] = nodeOutput
            }
        }
    }
}
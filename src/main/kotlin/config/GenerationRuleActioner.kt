package config

import util.ImageFileUtil

class GenerationRuleActioner {

    data class Input (
        val image: Array<IntArray>,
        val filename: String,
        val width: Int,
        val height: Int
    )

    lateinit var input: Input

    fun prepareInput(width: Int, height: Int): Input? {
        val image = ImageFileUtil().loadImage(width, height)
        if (image == null || image.bytes.size < 10 || image.bytes[0].size < 10) {
            return null
        }
        input = Input(image.bytes, image.filename, image.bytes[0].size, image.bytes.size)
        return input
    }

    fun prepareOutput(image: Array<IntArray>) {
        ImageFileUtil().save(image, input.filename)
    }

    fun performGenerationRules(rules: List<Config.GenerationRule>, tiles: List<Config.Tile>): Array<IntArray> {
        val solvedNodes = hashMapOf<String, Any>()

        // Add all tiles / precalced
        tiles.forEach {
            solvedNodes[it.name] = it
        }
        solvedNodes["inputImage"] = input.image
        solvedNodes["inputFilename"] = input.filename
        solvedNodes["inputWidth"] = input.width
        solvedNodes["inputHeight"] = input.height

        // Find concrete values
        val allOutputs = rules.map { it.outputId } + solvedNodes.keys
        val allDynamicInputs = rules.flatMap { it.inputMap.values }
        val concreteValues = allDynamicInputs - allOutputs.toSet()

        // Whilst we haven't solved the final node, keep trying
        while (!solvedNodes.contains("output")) {
            // Get all the nodes that haven't been solved yet
            val targetNodes = rules.filter { !solvedNodes.containsKey(it.outputId) }
            targetNodes.forEach { generationRule ->
                // For each one, if all needed inputs have been obtained, invoke it
                val ruleInputNames = generationRule.inputMap.values
                if ((ruleInputNames - solvedNodes.keys - concreteValues).isEmpty()) {
                    // Pull the outputs we need
                    val relevantSolvedNodes = solvedNodes
                        .filterKeys { ruleInputNames.contains(it) }
                        .toMutableMap()

                    // Add the concrete values we need
                    (ruleInputNames - relevantSolvedNodes.keys).forEach {
                        relevantSolvedNodes[it] = it.toIntOrNull() ?: it
                    }

                    // Prep them for use (should be key / value...)
                    val mapped = relevantSolvedNodes.toSortedMap(compareBy {
                        ruleInputNames.indexOf(it)
                    })

                    // Invoke function with the retrieved outputs
                    println("About to call ${generationRule.outputId} (${generationRule.rule}), we have ${relevantSolvedNodes.size} inputs")
                    val instance = generationRule.rule::class.objectInstance
                    val nodeOutput =
                        generationRule.rule::class.members.find { it.name == "invoke" }!!.call(
                            instance,
                            *mapped.values.toTypedArray()
                        )
                    // Add the result into solved nodes for future use
                    solvedNodes[generationRule.outputId] = nodeOutput!!
                }
            }
        }
        return solvedNodes["output"] as Array<IntArray>
    }
}
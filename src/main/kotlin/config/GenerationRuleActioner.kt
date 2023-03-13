package config

import rules.creator.InputImage
import rules.placer.OutputImage

class GenerationRuleActioner {

    fun performGenerationRules(rules: List<Config.GenerationRule>, tiles: List<Config.Tile>) {
        // Setup
        val inputNode = rules.first { it.rule == InputImage }
        val outputNode = rules.first { it.rule == OutputImage }
        val dummyTile = Config.Tile("", "", 0)
        val solvedNodes = hashMapOf<String, Any>()

        // Add all tiles / precalced
        tiles.forEach {
            solvedNodes[it.name] = it
        }
        solvedNodes[inputNode.outputId] = InputImage.invoke(emptyArray())

        // Whilst we haven't solved the final node, keep trying
        while (!solvedNodes.contains(outputNode.outputId)) {
            // Get all the nodes that haven't been solved yet
            val targetNodes = rules.filter { !solvedNodes.containsKey(it.outputId) }
            targetNodes.forEach { generationRule ->
                // For each one, if all needed inputs have been obtained, invoke it
                if (solvedNodes.keys.containsAll(generationRule.inputIds)) {
                    // Pull the outputs we need
                    val relevantSolvedNodes = solvedNodes
                        .filterKeys { generationRule.inputIds.contains(it) }
                        .toSortedMap(compareBy {
                            generationRule.inputIds.indexOf(it)
                        })

                    // Invoke function with the retrieved outputs
                    println("About to call ${generationRule.outputId} (${generationRule.rule}), we have ${relevantSolvedNodes.size} inputs")
                    val instance = generationRule.rule::class.objectInstance
                    val nodeOutput = if (generationRule.rule == OutputImage) {
                        OutputImage.invoke(relevantSolvedNodes.values.first() as Array<IntArray>, emptyArray(), dummyTile, dummyTile)
                    } else {
                        generationRule.rule::class.members.find { it.name == "invoke" }!!.call(
                            instance,
                            *relevantSolvedNodes.values.toTypedArray()
                        )
                    }
                    // Add the result into solved nodes, for future use
                    solvedNodes[generationRule.outputId] = nodeOutput!!
                }
            }
        }
    }
}
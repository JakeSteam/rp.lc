package config

import util.getInputParams
import util.getReturnType
import rules.creator.InputImage
import rules.placer.OutputImage
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf

class RuleValidator {

    fun identifyConfigErrors(config: Config): String? {
        identifyMetaErrors(config.meta)?.let { return it }
        identifyTileErrors(config.tiles)?.let { return it }
        identifyGenerationRuleFrequencyErrors(config.rules)?.let { return it }
        identifyGenerationRuleFlowErrors(config.tiles, config.rules)?.let { return it }
        return null
    }

    private fun identifyMetaErrors(meta: Config.Meta): String? {
        if (meta.name.isEmpty()) { return "Config name is empty" }
        if (meta.engineVersion.isEmpty()) { return "Config engine version is empty" }
        if (meta.author.isEmpty()) { return "Config author is empty" }
        return null
    }

    private fun identifyTileErrors(tiles: List<Config.Tile>): String? {
        tiles.forEach {
            if (it.name.isEmpty()) { return "Tile name is empty" }
            if (it.description.isEmpty()) { return "Tile description is empty" }
        }
        return null
    }

    private fun identifyGenerationRuleFrequencyErrors(rules: List<Config.GenerationRule>): String? {
        rules.singleOrNull { it.rule == InputImage } ?: return "More / less than one image input rule found"
        rules.singleOrNull { it.rule == OutputImage } ?: return "More / less than one image output rule found"
        return null
    }

    private fun identifyGenerationRuleFlowErrors(tiles: List<Config.Tile>, rules: List<Config.GenerationRule>): String? {
        val allTiles = tiles.map { it.name }
        val allInputs = rules.flatMap { it.inputIds }

        rules.forEach { generationRule ->
            if (generationRule.rule == InputImage) return@forEach
            if (generationRule.rule == OutputImage) return@forEach // No! Need to check inputs!

            // Check all inputs are provided
            val inputParamsNeeded = generationRule.rule.getInputParams()
            val inputParamsProvided = generationRule.inputIds.map { inputId ->
                if (allTiles.contains(inputId)) {
                    Config.Tile::class.createType()
                } else {
                    rules.first { it.outputId == inputId }.rule.getReturnType()
                }
            }
            inputParamsNeeded.forEachIndexed { index, needed ->
                val provided = inputParamsProvided.getOrNull(index)
                    ?: return "No value provided for ${needed.name} in ${generationRule.rule}"
                if (needed.type != provided && needed.type.isSubtypeOf(provided)) {
                    return "${needed.name} required ${needed.type}, but received $provided"
                }
            }

            // Check output is utilised
            allInputs.contains(generationRule.outputId)

            // Check no infinite loops
        }

        return null
    }
}
package config

import util.getInputParams
import util.getReturnType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType

class RuleValidator {

    fun identifyConfigErrors(config: Config): String? {
        identifyMetaErrors(config.meta)?.let { return it }
        identifyTileErrors(config.tiles, config.resources)?.let { return it }
        identifyResourceErrors(config.resources)?.let { return it }
        identifyGenerationRuleFlowErrors(config.tiles, config.rules)?.let { return it }
        return null
    }

    private fun identifyMetaErrors(meta: Config.Meta): String? {
        if (meta.name.isEmpty()) { return "Config name is empty" }
        if (meta.engineVersion.isEmpty()) { return "Config engine version is empty" }
        if (meta.author.isEmpty()) { return "Config author is empty" }
        return null
    }

    private fun identifyTileErrors(tiles: List<Config.Tile>, resources: List<Config.Resource>): String? {
        val allResourceNames = resources.map { it.name }
        tiles.forEach { tile ->
            if (tile.name.isEmpty()) { return "Tile name is empty" }
            if (tile.description.isEmpty()) { return "Tile description is empty" }
            tile.resourceChanges.forEach {
                if (!allResourceNames.contains(it.resource)) {
                    return "${tile.name} uses non-existent resource ${it.resource}"
                }
            }
        }
        return null
    }

    private fun identifyResourceErrors(resources: List<Config.Resource>): String? {
        resources.forEach {
            if (it.name.isEmpty()) { return "Tile name is empty" }
            if (it.description.isEmpty()) { return "Tile description is empty" }
        }
        return null
    }

    private fun identifyGenerationRuleFlowErrors(tiles: List<Config.Tile>, rules: List<Config.GenerationRule>): String? {
        val allTiles = tiles.map { it.name }
        val allInputs = rules.flatMap { it.inputIds }

        rules.forEach { generationRule ->
            // Check all inputs are provided
            val inputParamsNeeded = generationRule.rule.getInputParams()
            val inputParamsProvided = generationRule.inputIds.map { inputId ->
                if (allTiles.contains(inputId)) {
                    Config.Tile::class.createType()
                } else if (inputId == "inputFilename") {
                    String::class.createType()
                } else if (inputId == "inputWidth" || inputId == "inputHeight") {
                    Int::class.createType()
                } else if (inputId == "inputImage") {
                    val intArray = IntArray::class.starProjectedType
                    val projection = KTypeProjection.invariant(intArray)
                    Array::class.createType(listOf(projection))
                } else{
                    rules.firstOrNull { it.outputId == inputId }?.rule?.getReturnType()
                        ?: return "No value provided for $inputId, needed by ${generationRule.outputId}"
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
package config

import util.getInputParams
import util.getReturnType
import kotlin.reflect.KType
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
        val allInputs = rules.flatMap { it.inputMap.keys }

        // TODO: This check is inefficient now we have the param IDs
        rules.forEach { generationRule ->
            // Map all parameters to their types
            val inputParamsProvided = generationRule.inputMap.map { inputId ->
                val type: KType = if (allTiles.contains(inputId.value)) {
                    Config.Tile::class.createType()
                } else if (inputId.value == "inputFilename") {
                    String::class.createType()
                } else if (inputId.value == "inputWidth" || inputId.value == "inputHeight") {
                    Int::class.createType()
                } else if (inputId.value == "inputImage") {
                    val intArray = IntArray::class.starProjectedType
                    val projection = KTypeProjection.invariant(intArray)
                    Array::class.createType(listOf(projection))
                } else {
                    // Check passed variable
                    rules.firstOrNull { it.outputId == inputId.value }?.rule?.getReturnType()
                        ?: String::class.createType()
                }
                Triple(inputId.key, inputId.value, type)
            }

            // Check all inputs are provided
            generationRule.rule.getInputParams().forEach { needed ->
                val provided = inputParamsProvided.firstOrNull { it.first == needed.name }
                    ?: return "No value provided for ${needed.name} in ${generationRule.rule}"
                if (!(needed.type == provided.third) &&
                    !(needed.type.isSubtypeOf(provided.third)) &&
                    !(needed.type == Int::class.createType() && provided.second.toIntOrNull() != null)) {
                    return "${generationRule.rule}'s ${needed.name} required ${needed.type}, but received a ${provided.second}"
                }
            }

            // Check output is utilised
            if (!allInputs.contains(generationRule.outputId)) {
                return "${generationRule.outputId} is generated but not used anywhere."
            }

            // Check no infinite loops
        }

        return null
    }
}
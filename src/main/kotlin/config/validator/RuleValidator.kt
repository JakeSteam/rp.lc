package config.validator

import config.schema.Config
import config.util.getInputParams
import config.util.getReturnType
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf

class RuleValidator {

    fun identifyConfigErrors(config: Config): String? {
        identifyMetaErrors(config.meta)?.let { return it }
        identifyTileErrors(config.tiles)
        identifyGenerationRuleErrors(config.tiles, config.rules)
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

    private fun identifyGenerationRuleErrors(tiles: List<Config.Tile>, rules: List<Config.GenerationRule>): String? {
        val allTiles = tiles.map { it.name }
        rules.forEach { generationRule ->
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
        }
        return null
    }
}
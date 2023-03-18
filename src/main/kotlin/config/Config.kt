package config

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val meta: Meta,
    val gameConfig: GameConfig,
    val tiles: List<Tile>,
    val resources: List<Resource>,
    val rules: List<GenerationRule>
) {

    @Serializable
    data class Meta(
        val name: String,
        val engineVersion: String,
        val author: String,
        val authorUrl: String?
    )

    @Serializable
    data class GameConfig(
        val requiredScorePerRank: List<Pair<Int, String>>,
        val outputWidth: Int,
        val outputHeight: Int
    )

    @Serializable
    data class Tile (
        val name: String,
        val description: String,
        val colour: Int,
        val resourceChanges: List<ResourceChange>
    ) {
        @Serializable
        data class ResourceChange (
            val resource: String,
            val changePerTurn: Int
        )
    }

    @Serializable
    data class Resource (
        val name: String,
        val description: String,
        val colour: Int,
        val scoreImpact: Int
    )

    @Serializable
    data class GenerationRule (
        val rule: rules.BaseRule,
        val outputId: String,
        val inputIds: ArrayList<String>,
    )
}

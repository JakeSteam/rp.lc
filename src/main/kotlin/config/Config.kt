package config

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
data class Config(val meta: Meta, val tiles: List<Tile>, val rules: List<GenerationRule>) {

    @Serializable
    data class Meta (
        val name: String,
        val engineVersion: String,
        val author: String,
        val authorUrl: String?
    )

    @Serializable
    data class Tile (
        val name: String,
        val description: String,
        val colour: Int
    )

    @Serializable
    data class GenerationRule (
        val rule: rules.BaseRule,
        val outputId: String,
        val inputIds: ArrayList<String>,
    )
}

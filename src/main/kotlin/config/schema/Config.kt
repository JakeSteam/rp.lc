package config.schema

import pixel.Rule
import pixel.analyser.Analyser
import java.awt.Color

data class Config(val meta: Meta, val tiles: List<Tile>, val rules: List<GenerationRule>) {

    data class Meta (
        val name: String,
        val author: String,
        val authorUrl: String,
        val engineVersion: String
    )

    data class Tile (
        val name: String,
        val description: String,
        val colour: Int
    )

    data class GenerationRule (
        val rule: Rule,
        val outputId: String,
        val inputIds: List<String>,
    )
}

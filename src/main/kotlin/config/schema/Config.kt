package config.schema

import pixel.read.ReadRule
import java.awt.Color

data class Config(val meta: Meta, val tiles: List<Tile>) {

    data class Meta (
        val name: String,
        val author: String,
        val authorUrl: String,
        val engineVersion: Int
    )

    data class Tile (
        val name: String,
        val description: String,
        val colour: Color,
        val rules: List<Rule>
    )

    // Rule needs to say if it applies to input or current output, and the order(?)
    data class Rule (
        val readRule: ReadRule
    )
}
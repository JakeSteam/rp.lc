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
        val generationRules: List<GenerationRule>
    )

    data class GenerationRule (
        val inputId: String,
        val outputId: String,
        val readRule: ReadRule
    )
}

/*
Config defines generation rules.
Generation rules take an input and an output, what are their types? For example:
- Take image as input, apply analyser `mostcommon`, get colour out
- Take image AND colour as input, apply transformer `colourmatches`, get black / white matrix out
- Take true / false matrix AND true colour AND false colour as input, apply placer `booleanplacer`

Notes:
- No artificial priority is needed, the order can be defined based on references
- Whilst referencing each other will be tricky, it can be solved with arbitrary IDs
- Analyser / Transformer / Placer should each extend a class, then each be extended by things like MostCommon
- HOW can a rule define what it needed? For example mostcommon just needs image... how on earth to map from JSON
 */

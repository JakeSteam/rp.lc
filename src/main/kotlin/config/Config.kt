package config

data class Config(val meta: Meta, val tiles: List<Tile>, val rules: List<GenerationRule>) {

    data class Meta (
        val name: String,
        val engineVersion: String,
        val author: String,
        val authorUrl: String?
    )

    data class Tile (
        val name: String,
        val description: String,
        val colour: Int
    )

    data class GenerationRule (
        val rule: generation.BaseRule,
        val outputId: String,
        val inputIds: ArrayList<String>,
    )
}

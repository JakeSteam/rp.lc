package pixel.generator

object Blank : Generator {

    override fun create() : Array<IntArray> {
        return Array(97) { IntArray(73) }
    }
}
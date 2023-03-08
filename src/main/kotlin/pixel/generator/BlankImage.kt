package pixel.generator

object BlankImage : Generator {

    override fun create() : Array<IntArray> {
        return Array(97) { IntArray(73) }
    }
}
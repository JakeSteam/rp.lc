package pixel.generator

object Blank : Generator {

    override fun create(width: Int, height: Int) : Array<IntArray> {
        return Array(width) { IntArray(height) }
    }
}
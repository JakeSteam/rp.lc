package pixel.placer

object ApplyMask : Placer {
    override fun place(image: Array<IntArray>, mask: Array<BooleanArray>, ifTrue: Int, ifFalse: Int): Array<IntArray> {
        for (y in mask.indices) {
            for (x in mask[0].indices) {
                val shouldApply = mask[y][x]
                image[y][x] = if (shouldApply) ifTrue else ifFalse
            }
        }
        return image
    }
}
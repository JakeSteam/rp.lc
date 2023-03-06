package image

class ImageLog {
    companion object {
        fun pixel(x: Int, y: Int, colour: Int) =
            d("Saved x: $x, y: $y as $colour")

        fun d(message: String) = println("Image: $message")
    }
}
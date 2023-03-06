package file

class FileLog {
    companion object {
        fun pixel(x: Int, y: Int, colour: Int) =
            d("Saved x: $x, y: $y as $colour")

        fun d(message: String) = println("File: $message")
    }
}
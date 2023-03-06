package pixel.read

class FetchLog {
    companion object {
        fun pixel(x: Int, y: Int, colour: Int) =
            d("Checked x: $x, y: $y, it was $colour")

        fun d(message: String) = println("Fetch: $message")
    }
}
package pixel.colour

import java.awt.Color

class HexReader {

    companion object {
        fun toColor(hex: String): Color? {
            var hex = hex
            hex = hex.replace("#", "")
            when (hex.length) {
                6 -> return Color(
                    Integer.valueOf(hex.substring(0, 2), 16),
                    Integer.valueOf(hex.substring(2, 4), 16),
                    Integer.valueOf(hex.substring(4, 6), 16)
                )

                8 -> return Color(
                    Integer.valueOf(hex.substring(0, 2), 16),
                    Integer.valueOf(hex.substring(2, 4), 16),
                    Integer.valueOf(hex.substring(4, 6), 16),
                    Integer.valueOf(hex.substring(6, 8), 16)
                )
            }
            return null
        }
    }
}
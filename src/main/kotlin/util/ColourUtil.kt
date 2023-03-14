package util

import java.awt.Color

class ColourUtil {

    companion object {
        fun toColor(hex: String): Color? {
            val safeHex = hex.replace("#", "")
            when (safeHex.length) {
                // RGB, e.g. #00FF00
                6 -> return Color(
                    Integer.valueOf(safeHex.substring(0, 2), 16),
                    Integer.valueOf(safeHex.substring(2, 4), 16),
                    Integer.valueOf(safeHex.substring(4, 6), 16)
                )
                // RGBA, e.g. #00FF00AA
                8 -> return Color(
                    Integer.valueOf(safeHex.substring(0, 2), 16),
                    Integer.valueOf(safeHex.substring(2, 4), 16),
                    Integer.valueOf(safeHex.substring(4, 6), 16),
                    Integer.valueOf(safeHex.substring(6, 8), 16)
                )
            }
            return null
        }
    }
}
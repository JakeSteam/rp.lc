package util

import config.Config

class TileUtil {
    companion object {
        fun coloursToTiles(tiles: List<Config.Tile>, image: Array<IntArray>): Array<Array<Config.Tile>> {
            val imageTiles = Array(image.size) {
                Array(image[0].size) {
                    Config.Tile("", "", 0, emptyList())
                }
            }
            val colourMap = tiles.associateBy { it.colour }
            for (y in image.indices) {
                for (x in image[0].indices) {
                    imageTiles[y][x] = colourMap[image[y][x]]
                        ?: throw ArrayIndexOutOfBoundsException("Failed to find $y/$x in colour map")
                }
            }
            return imageTiles
        }
    }
}
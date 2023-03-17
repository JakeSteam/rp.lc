package resources

import config.Config

class ResourceCalculator {

    companion object {
        fun tilesToResourceChanges(tiles: Array<Array<Config.Tile>>, resources: List<Config.Resource>): Map<Config.Resource, Int> {
            val stringToResource = resources.associateBy { it.name }
            val result: MutableMap<Config.Resource, Int> = HashMap()
            tiles.forEach { it.forEach { tile ->
                tile.resourceChanges.forEach { change ->
                    val resource = stringToResource[change.resource]
                        ?: throw ArrayIndexOutOfBoundsException("Couldn't find ${change.resource}")
                    result.merge(resource, change.changePerTurn, Int::plus)
                }
            } }
            return result
        }
    }
}
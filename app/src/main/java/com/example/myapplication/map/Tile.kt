package com.example.myapplication.map

import android.graphics.Canvas
import android.graphics.Rect
import com.example.myapplication.graphics.SpriteSheet

internal abstract class Tile(@JvmField protected val mapLocationRect: Rect?) {
    enum class TileType {
        WATER_TILE,
        LAVA_TILE,
        GROUND_TILE,
        GRASS_TILE,
        TREE_TILE
    }

    abstract fun draw(canvas: Canvas?)

    companion object {
        @JvmStatic
        fun getTile(idxTileType: Int, spriteSheet: SpriteSheet?, mapLocationRect: Rect?): Tile? {
            return when (TileType.entries[idxTileType]) {
                TileType.WATER_TILE -> spriteSheet?.let { WaterTile(it, mapLocationRect) }
                TileType.LAVA_TILE -> LavaTile(spriteSheet!!, mapLocationRect)
                TileType.GROUND_TILE -> GroundTile(spriteSheet!!, mapLocationRect)
                TileType.GRASS_TILE -> GrassTile(spriteSheet!!, mapLocationRect)
                TileType.TREE_TILE -> spriteSheet?.let { TreeTile(it, mapLocationRect) }
                else -> null
            }
        }
    }
}

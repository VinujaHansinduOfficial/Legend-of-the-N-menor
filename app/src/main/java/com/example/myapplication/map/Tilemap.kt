package com.example.myapplication.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.example.myapplication.GameDisplay
import com.example.myapplication.graphics.SpriteSheet
import com.example.myapplication.map.Tile.Companion.getTile

class Tilemap(spriteSheet: SpriteSheet) {
    private val mapLayout: MapLayout
    private lateinit var tilemap: Array<Array<Tile?>>
    private val spriteSheet: SpriteSheet
    private var mapBitmap: Bitmap? = null

    init {
        mapLayout = MapLayout()
        this.spriteSheet = spriteSheet
        initializeTilemap()
    }

    private fun initializeTilemap() {
        val layout = mapLayout.layout
        tilemap =
            Array(MapLayout.NUMBER_OF_ROW_TILES) { arrayOfNulls(MapLayout.NUMBER_OF_COLUMN_TILES) }
        for (iRow in 0 until MapLayout.NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until MapLayout.NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol] = getTile(
                    layout[iRow][iCol],
                    spriteSheet,
                    getRectByIndex(iRow, iCol)
                )
            }
        }
        val config = Bitmap.Config.ARGB_8888
        mapBitmap = Bitmap.createBitmap(
            MapLayout.NUMBER_OF_COLUMN_TILES * MapLayout.TILE_WIDTH_PIXELS,
            MapLayout.NUMBER_OF_ROW_TILES * MapLayout.TILE_HEIGHT_PIXELS,
            config
        )
        val mapCanvas = Canvas(mapBitmap!!)
        for (iRow in 0 until MapLayout.NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until MapLayout.NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol]!!.draw(mapCanvas)
            }
        }
    }

    private fun getRectByIndex(idxRow: Int, idxCol: Int): Rect {
        return Rect(
            idxCol * MapLayout.TILE_WIDTH_PIXELS,
            idxRow * MapLayout.TILE_HEIGHT_PIXELS,
            (idxCol + 1) * MapLayout.TILE_WIDTH_PIXELS,
            (idxRow + 1) * MapLayout.TILE_HEIGHT_PIXELS
        )
    }

    fun draw(canvas: Canvas, gameDisplay: GameDisplay) {
        canvas.drawBitmap(
            mapBitmap!!,
            gameDisplay.gameRect,
            gameDisplay.DISPLAY_RECT,
            null
        )
    }
}

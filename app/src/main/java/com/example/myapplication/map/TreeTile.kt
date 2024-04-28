package com.example.myapplication.map

import android.graphics.Canvas
import android.graphics.Rect
import com.example.myapplication.graphics.Sprite
import com.example.myapplication.graphics.SpriteSheet

internal class TreeTile(spriteSheet: SpriteSheet, mapLocationRect: Rect?) : Tile(
    mapLocationRect!!
) {
    private val grassSprite: Sprite
    private val treeSprite: Sprite

    init {
        grassSprite = spriteSheet.grassSprite
        treeSprite = spriteSheet.treeSprite
    }

    override fun draw(canvas: Canvas?) {
        mapLocationRect?.let { grassSprite.draw(canvas!!, it.left, mapLocationRect.top) }
        mapLocationRect?.let {
            if (canvas != null) {
                treeSprite.draw(canvas, it.left, mapLocationRect.top)
            }
        }
    }
}

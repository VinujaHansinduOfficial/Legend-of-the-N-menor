package com.example.myapplication.map

import android.graphics.Canvas
import android.graphics.Rect
import com.example.myapplication.graphics.Sprite
import com.example.myapplication.graphics.SpriteSheet

internal class WaterTile(spriteSheet: SpriteSheet, mapLocationRect: Rect?) : Tile(
    mapLocationRect!!
) {
    private val sprite: Sprite

    init {
        sprite = spriteSheet.waterSprite
    }

    override fun draw(canvas: Canvas?) {
        mapLocationRect?.let { sprite.draw(canvas!!, it.left, mapLocationRect.top) }
    }
}

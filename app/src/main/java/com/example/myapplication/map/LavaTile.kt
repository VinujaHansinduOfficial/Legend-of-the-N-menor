package com.example.myapplication.map

import android.graphics.Canvas
import android.graphics.Rect
import com.example.myapplication.graphics.Sprite
import com.example.myapplication.graphics.SpriteSheet

internal class LavaTile(spriteSheet: SpriteSheet, mapLocationRect: Rect?) : Tile(mapLocationRect) {
    private val sprite: Sprite

    init {
        sprite = spriteSheet.lavaSprite
    }

    override fun draw(canvas: Canvas?) {
        if (canvas != null) {
            mapLocationRect?.let { sprite.draw(canvas, it.left, mapLocationRect.top) }
        }
    }
}

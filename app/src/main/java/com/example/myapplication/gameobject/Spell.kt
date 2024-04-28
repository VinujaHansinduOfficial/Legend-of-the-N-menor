package com.example.myapplication.gameobject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.myapplication.GameDisplay
import com.example.myapplication.GameLoop
import com.example.myapplication.R

class Spell(context: Context, spellcaster: Player) : Circle(
    context,
    0,
    spellcaster.positionX,
    spellcaster.positionY,
    25.0
) {
    private val spellBitmap: Bitmap

    init {
        spellBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.spell)
        velocityX = spellcaster.directionX * MAX_SPEED
        velocityY = spellcaster.directionY * MAX_SPEED
    }

    override fun draw(canvas: Canvas?, gameDisplay: GameDisplay?) {
        if (canvas != null) {
            if (gameDisplay != null) {
                canvas.drawBitmap(
                    spellBitmap,
                    gameDisplay.gameToDisplayCoordinatesX(positionX).toFloat() - spellBitmap.width / 2,
                    gameDisplay.gameToDisplayCoordinatesY(positionY).toFloat() - spellBitmap.height / 2,
                    null
                )
            }
        }
    }

    override fun update() {
        positionX += velocityX
        positionY += velocityY
    }

    companion object {
        const val SPEED_PIXELS_PER_SECOND = 800.0
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
    }
}

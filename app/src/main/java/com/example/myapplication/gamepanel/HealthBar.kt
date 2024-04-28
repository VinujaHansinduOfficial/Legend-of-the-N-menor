package com.example.myapplication.gamepanel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.example.myapplication.GameDisplay
import com.example.myapplication.R
import com.example.myapplication.gameobject.Player


class HealthBar(context: Context?, private val player: Player) {
    private val borderPaint: Paint
    private val healthPaint: Paint
    private val width = 100
    private val height = 20
    private val margin = 2

    init {
        borderPaint = Paint()
        val borderColor = ContextCompat.getColor(context!!, R.color.healthBarBorder)
        borderPaint.color = borderColor
        healthPaint = Paint()
        val healthColor = ContextCompat.getColor(context, R.color.healthBarHealth)
        healthPaint.color = healthColor
    }

    fun draw(canvas: Canvas, gameDisplay: GameDisplay) {
        val x = player.positionX.toFloat()
        val y = player.positionY.toFloat()
        val distanceToPlayer = 30f
        val healthPointPercentage = player.healthPoint.toFloat() / Player.MAX_HEALTH_POINTS

        val borderLeft: Float
        val borderTop: Float
        val borderRight: Float
        val borderBottom: Float
        borderLeft = x - width / 2
        borderRight = x + width / 2
        borderBottom = y - distanceToPlayer
        borderTop = borderBottom - height
        canvas.drawRect(
            gameDisplay.gameToDisplayCoordinatesX(borderLeft.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(borderTop.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesX(borderRight.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(borderBottom.toDouble()).toFloat(),
            borderPaint
        )


        val healthLeft: Float
        val healthTop: Float
        val healthRight: Float
        val healthBottom: Float
        val healthWidth: Float
        val healthHeight: Float
        healthWidth = (width - 2 * margin).toFloat()
        healthHeight = (height - 2 * margin).toFloat()
        healthLeft = borderLeft + margin
        healthRight = healthLeft + healthWidth * healthPointPercentage
        healthBottom = borderBottom - margin
        healthTop = healthBottom - healthHeight
        canvas.drawRect(
            gameDisplay.gameToDisplayCoordinatesX(healthLeft.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(healthTop.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesX(healthRight.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(healthBottom.toDouble()).toFloat(),
            healthPaint
        )
    }
}

package com.example.myapplication.gameobject

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.example.myapplication.GameDisplay
import com.example.myapplication.GameLoop
import com.example.myapplication.R
import com.example.myapplication.Utils.getDistanceBetweenPoints
import com.example.myapplication.gamepanel.HealthBar
import com.example.myapplication.gamepanel.Joystick
import com.example.myapplication.graphics.Animator

class Player(
    context: Context?,
    private val joystick: Joystick,
    positionX: Double,
    positionY: Double,
    radius: Double,
    animator: Animator,
    mapWidth: Int,
    mapHeight: Int
) : Circle(
    context, ContextCompat.getColor(
        context!!, R.color.player
    ), positionX, positionY, radius
) {
    private val healthBar: HealthBar
    private var healthPoints = MAX_HEALTH_POINTS
    private val animator: Animator
    val playerState: PlayerState
    private val mapWidth: Int
    private val mapHeight: Int

    init {
        healthBar = HealthBar(context, this)
        this.animator = animator
        playerState = PlayerState(this)
        this.mapWidth = mapWidth
        this.mapHeight = mapHeight
    }

    override fun update() {
        val velocityX = joystick.actuatorX * MAX_SPEED
        val velocityY = joystick.actuatorY * MAX_SPEED

        val newX = positionX + velocityX
        val newY = positionY + velocityY

        if (isWithinMapBounds(newX, newY)) {
            positionX = newX
            positionY = newY
        }

        if (velocityX != 0.0 || velocityY != 0.0) {
            val distance = getDistanceBetweenPoints(0.0, 0.0, velocityX, velocityY)
            directionX = velocityX / distance
            directionY = velocityY / distance
        }
        playerState.update()
    }

    private fun isWithinMapBounds(x: Double, y: Double): Boolean {
        return x >= 0 && y >= 0 && x <= mapWidth && y <= mapHeight
    }

    override fun draw(canvas: Canvas?, gameDisplay: GameDisplay?) {
        if (gameDisplay != null) {
            if (canvas != null) {
                animator.draw(canvas, gameDisplay, this)
            }
        }
        if (canvas != null) {
            if (gameDisplay != null) {
                healthBar.draw(canvas, gameDisplay)
            }
        }
    }

    var healthPoint: Int
        get() = healthPoints
        set(healthPoints) {
            // Only allow positive values
            if (healthPoints >= 0) this.healthPoints = healthPoints
        }

    companion object {
        const val SPEED_PIXELS_PER_SECOND = 400.0
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
        const val MAX_HEALTH_POINTS = 5
    }
}

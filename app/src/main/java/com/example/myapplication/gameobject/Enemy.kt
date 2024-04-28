package com.example.myapplication.gameobject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.example.myapplication.GameDisplay
import com.example.myapplication.GameLoop
import com.example.myapplication.R

class Enemy : Circle {
    private var player: Player
    private var enemyBitmap: Bitmap? = null

    constructor(
        context: Context?,
        player: Player,
        positionX: Double,
        positionY: Double,
        radius: Double
    ) : super(
        context, ContextCompat.getColor(
            context!!, R.color.enemy
        ), positionX, positionY, radius
    ) {
        this.player = player
    }
    constructor(context: Context, player: Player) : super(
        context,
        0,
        Math.random() * 1000,
        Math.random() * 1000,
        30.0
    ) {
        enemyBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.enemy)
        this.player = player
    }

    override fun draw(canvas: Canvas?, gameDisplay: GameDisplay?) {
        canvas!!.drawBitmap(
            enemyBitmap!!,
            gameDisplay!!.gameToDisplayCoordinatesX(positionX).toFloat() - enemyBitmap!!.width / 2,
            gameDisplay.gameToDisplayCoordinatesY(positionY).toFloat() - enemyBitmap!!.height / 2,
            null
        )
    }

    override fun update() {
        val distanceToPlayerX = player.positionX - positionX
        val distanceToPlayerY = player.positionY - positionY

        val distanceToPlayer = getDistanceBetweenObjects(this, player)

        val directionX = distanceToPlayerX / distanceToPlayer
        val directionY = distanceToPlayerY / distanceToPlayer

        if (distanceToPlayer > 0) {
            velocityX = directionX * MAX_SPEED
            velocityY = directionY * MAX_SPEED
        } else {
            velocityX = 0.0
            velocityY = 0.0
        }
        positionX += velocityX
        positionY += velocityY
    }

    companion object {
        private const val SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND * 0.4
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
        private const val SPAWNS_PER_MINUTE = 20.0
        private const val SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0
        private const val UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND
        private var updatesUntilNextSpawn = UPDATES_PER_SPAWN


        fun readyToSpawn(): Boolean {
            return if (updatesUntilNextSpawn <= 0) {
                updatesUntilNextSpawn += UPDATES_PER_SPAWN
                true
            } else {
                updatesUntilNextSpawn--
                false
            }
        }
    }
}
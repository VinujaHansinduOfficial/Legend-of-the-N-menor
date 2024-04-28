package com.example.myapplication.gameobject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.example.myapplication.GameDisplay


abstract class Circle(
    context: Context?,
    color: Int,
    positionX: Double,
    positionY: Double,
    var radius: Double
) : GameObject(positionX, positionY) {
    protected var paint: Paint

    init {
        paint = Paint()
        paint.color = color
    }

    override fun draw(canvas: Canvas?, gameDisplay: GameDisplay?) {
        canvas!!.drawCircle(
            gameDisplay!!.gameToDisplayCoordinatesX(positionX).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(positionY).toFloat(),
            radius.toFloat(),
            paint
        )
    }

    companion object {
        fun isColliding(obj1: Circle, obj2: Circle): Boolean {
            val distance = getDistanceBetweenObjects(obj1, obj2)
            val distanceToCollision = obj1.radius + obj2.radius
            return if (distance < distanceToCollision) true else false
        }
    }
}
package com.example.myapplication.gameobject

import android.graphics.Canvas
import com.example.myapplication.GameDisplay


abstract class GameObject {
    var positionX = 0.0
        protected set
    var positionY = 0.0
        protected set
    @JvmField
    var velocityX = 0.0
    @JvmField
    var velocityY = 0.0
    var directionX = 1.0
        protected set
    var directionY = 0.0
        protected set

    constructor()
    constructor(positionX: Double, positionY: Double) {
        this.positionX = positionX
        this.positionY = positionY
    }

    abstract fun draw(canvas: Canvas?, gameDisplay: GameDisplay?)
    abstract fun update()

    companion object {
        @JvmStatic
        fun getDistanceBetweenObjects(obj1: GameObject, obj2: GameObject): Double {
            return Math.sqrt(
                Math.pow(obj2.positionX - obj1.positionX, 2.0) +
                        Math.pow(obj2.positionY - obj1.positionY, 2.0)
            )
        }
    }
}

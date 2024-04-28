package com.example.myapplication

object Utils {
    @JvmStatic
    fun getDistanceBetweenPoints(p1x: Double, p1y: Double, p2x: Double, p2y: Double): Double {
        return Math.sqrt(Math.pow(p1x - p2x, 2.0) + Math.pow(p1y - p2y, 2.0))
    }
}

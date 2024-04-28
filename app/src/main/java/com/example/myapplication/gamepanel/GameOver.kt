package com.example.myapplication.gamepanel

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.example.myapplication.Menu
import com.example.myapplication.R

class GameOver(private val context: Context) {
    fun draw(canvas: Canvas) {
        val text = "Game Over"
        val x = 600f
        val y = 500f

        val intent = Intent(context, Menu::class.java)
        context.startActivity(intent)
        val paint = Paint()
        val color = ContextCompat.getColor(context, R.color.gameOver)
        paint.color = color
        val textSize = 150f
        paint.textSize = textSize
        canvas.drawText(text, x, y, paint)
    }
}

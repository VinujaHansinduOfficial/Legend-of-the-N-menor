package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.myapplication.gameobject.Circle
import com.example.myapplication.gameobject.Enemy
import com.example.myapplication.gameobject.Player
import com.example.myapplication.gameobject.Spell
import com.example.myapplication.gamepanel.GameOver
import com.example.myapplication.gamepanel.Joystick
import com.example.myapplication.graphics.Animator
import com.example.myapplication.graphics.SpriteSheet
import com.example.myapplication.map.Tilemap

//import com.example.myapplication.gamepanel.Performance;
class Game(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private val tilemap: Tilemap
    private var joystickPointerId = 0
    private val joystick: Joystick
    private val player: Player
    private var gameLoop: GameLoop
    private val enemyList: MutableList<Enemy> = ArrayList()
    private val spellList: MutableList<Spell> = ArrayList()
    private var numberOfSpellsToCast = 0
    private val gameOver: GameOver

    //private Performance performance;
    private val gameDisplay: GameDisplay
    private val spriteSheet: SpriteSheet? = null
    private var score = 0
    private val sharedPreferences: SharedPreferences

    init {
        val surfaceHolder = holder
        surfaceHolder.addCallback(this)
        gameLoop = GameLoop(this, surfaceHolder)
        //performance = new Performance(context, gameLoop);
        gameOver = GameOver(context)
        val displayMetrics = DisplayMetrics()
        (getContext() as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        joystick = Joystick(displayMetrics.widthPixels/8, displayMetrics.heightPixels-260, 120, 40)
        val spriteSheet = SpriteSheet(context)
        val animator = Animator(spriteSheet.playerSpriteArray)
        player = Player(context, joystick, (2 * 500).toDouble(), 500.0, 100.0, animator, 64 * 60, 64 * 60)
        gameDisplay = GameDisplay(displayMetrics.widthPixels-20, displayMetrics.heightPixels, player)
        tilemap = Tilemap(spriteSheet)
        isFocusable = true

        // Initialize SharedPreferences
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Handle user input touch event actions
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                if (joystick.isPressed) {
                    numberOfSpellsToCast++
                } else if (joystick.isPressed(event.x.toDouble(), event.y.toDouble())) {
                    joystickPointerId = event.getPointerId(event.actionIndex)
                    joystick.isPressed = true
                } else {
                    numberOfSpellsToCast++
                }
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (joystick.isPressed) {
                    joystick.setActuator(event.x.toDouble(), event.y.toDouble())
                }
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                if (joystickPointerId == event.getPointerId(event.actionIndex)) {
                    joystick.isPressed = false
                    joystick.resetActuator()
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (gameLoop.state == Thread.State.TERMINATED) {
            val surfaceHolder = getHolder()
            surfaceHolder.addCallback(this)
            gameLoop = GameLoop(this, surfaceHolder)
        }
        gameLoop.startLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        tilemap.draw(canvas, gameDisplay)
        player.draw(canvas, gameDisplay)
        for (enemy in enemyList) {
            enemy.draw(canvas, gameDisplay)
        }
        for (spell in spellList) {
            spell.draw(canvas, gameDisplay)
        }
        joystick.draw(canvas)
        //performance.draw(canvas);
        if (player.healthPoint <= 0) {
            gameOver.draw(canvas)
        }
        val paint = Paint()
        paint.color = Color.WHITE
        paint.textSize = 50f
        canvas.drawText("Score: $score", 50f, 50f, paint)

        // Display high score on the canvas
        val highScore = highScore
        canvas.drawText("High Score: $highScore", 50f, 120f, paint)
    }

    fun update() {
        if (player.healthPoint <= 0) {
            return
        }
        joystick.update()
        player.update()
        if (Enemy.readyToSpawn()) {
            enemyList.add(Enemy(context, player))
        }
        for (enemy in enemyList) {
            enemy.update()
        }
        while (numberOfSpellsToCast > 0) {
            spellList.add(Spell(context, player))
            numberOfSpellsToCast--
        }
        for (spell in spellList) {
            spell.update()
        }
        val iteratorEnemy = enemyList.iterator()
        while (iteratorEnemy.hasNext()) {
            val enemy = iteratorEnemy.next()
            var enemyHit = false
            if (Circle.isColliding(enemy, player)) {
                iteratorEnemy.remove()
                player.healthPoint = player.healthPoint - 1
                continue
            }
            val iteratorSpell = spellList.iterator()
            while (iteratorSpell.hasNext()) {
                val spell = iteratorSpell.next()
                if (Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove()
                    iteratorEnemy.remove()
                    enemyHit = true
                    break
                }
            }
            if (enemyHit) {
                score += 1
                if (score > highScore) {
                    saveHighScore(score)
                }
            }
        }
        gameDisplay.update()
    }

    fun pause() {
        gameLoop.stopLoop()
    }

    // Method to save the high score
    private fun saveHighScore(highScore: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(HIGH_SCORE_KEY, highScore)
        editor.apply()
    }

    private val highScore: Int
        // Method to retrieve the high score
        private get() = sharedPreferences.getInt(HIGH_SCORE_KEY, 0)

    companion object {
        private const val HIGH_SCORE_KEY = "HighScore"
    }
}

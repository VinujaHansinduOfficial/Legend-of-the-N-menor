package com.example.myapplication.graphics

import android.graphics.Canvas
import com.example.myapplication.GameDisplay
import com.example.myapplication.gameobject.Player
import com.example.myapplication.gameobject.PlayerState


class Animator(private val playerSpriteArray: Array<Sprite?>) {
    private var idxNotMovingFrame = 0
    private var idxMovingFrame = 1
    private var updatesBeforeNextMoveFrame = 0

    companion object {
        private const val MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME = 5
    }

    fun draw(canvas: Canvas, gameDisplay: GameDisplay, player: Player) {
        when (player.playerState.state) {
            PlayerState.State.NOT_MOVING -> playerSpriteArray[idxNotMovingFrame]?.let {
                drawFrame(canvas, gameDisplay, player,
                    it
                )
            }
            PlayerState.State.STARED_MOVING -> {
                updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME
                playerSpriteArray[idxMovingFrame]?.let {
                    drawFrame(canvas, gameDisplay, player,
                        it
                    )
                }
            }
            PlayerState.State.IS_MOVING -> {
                updatesBeforeNextMoveFrame--
                if (updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME
                    toggleIdxMovingFrame()
                }
                playerSpriteArray[idxMovingFrame]?.let {
                    drawFrame(canvas, gameDisplay, player,
                        it
                    )
                }
            }
            else -> {}
        }
    }

    private fun toggleIdxMovingFrame() {
        if(idxMovingFrame == 1)
            idxMovingFrame = 2;
        else
            idxMovingFrame = 1;
    }

    private fun drawFrame(canvas: Canvas, gameDisplay: GameDisplay, player: Player, sprite: Sprite) {
        sprite.draw(
            canvas,
            (gameDisplay.gameToDisplayCoordinatesX(player.positionX) - sprite.width / 2).toInt(),
            (gameDisplay.gameToDisplayCoordinatesY(player.positionY) - sprite.height / 2).toInt()
        )
    }
}
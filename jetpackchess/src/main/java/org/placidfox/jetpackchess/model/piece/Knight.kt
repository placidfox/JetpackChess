package org.placidfox.jetpackchess.model.piece

import org.placidfox.jetpackchess.R
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.model.game.GamePosition

class Knight(override val color: PlayerColor) : Piece {

    override val asset: Int =
        when (color) {
            PlayerColor.WHITE -> R.drawable.n_white
            PlayerColor.BLACK -> R.drawable.n_black
        }

    override val assetRatio: Float = 0.9f

    override val imgSymbol: String = when (color) {
        PlayerColor.WHITE -> "♘"
        PlayerColor.BLACK -> "♞"
    }

    override val textSymbol: String = "N"

    override val FENSymbol: String =
        when(color) {
            PlayerColor.WHITE -> "N"
            PlayerColor.BLACK -> "n"
        }

    override val value: Int = 3


    companion object {
        val targets = listOf(
            -8,
            +8,
            +12,
            -12,
            -19,
            +19,
            -21,
            +21
        )
    }


}
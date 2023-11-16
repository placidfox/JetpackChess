package org.placidfox.jetpackchess.model.piece

import org.placidfox.jetpackchess.R

class Rook(override val color: PlayerColor) : Piece {

    override val asset: Int =
        when (color) {
            PlayerColor.WHITE -> R.drawable.r_white
            PlayerColor.BLACK -> R.drawable.r_black
        }

    override val assetRatio: Float = 0.9f

    override val imgSymbol: String = when (color) {
        PlayerColor.WHITE -> "♖"
        PlayerColor.BLACK -> "♜"
    }

    override val textSymbol: String = "R"

    override val FENSymbol: String =
        when(color) {
            PlayerColor.WHITE -> "R"
            PlayerColor.BLACK -> "r"
        }

    override val value: Int = 5

    companion object {
        val directions = listOf(
            -10, +10,
            -1, +1
        )
    }


}
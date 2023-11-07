package org.placidfox.jetpackchess.model.piece
import org.placidfox.jetpackchess.R

class Bishop(override val color: PlayerColor) : Piece {

    override val asset: Int =
        when (color) {
            PlayerColor.WHITE -> R.drawable.b_white
            PlayerColor.BLACK -> R.drawable.b_black
        }

    override val assetRatio: Float = 0.9f

    override val imgSymbol: String = when (color) {
        PlayerColor.WHITE -> "♗"
        PlayerColor.BLACK -> "♝"
    }

    override val textSymbol: String = "B"

    override val FENSymbol: String =
        when(color) {
            PlayerColor.WHITE -> "B"
            PlayerColor.BLACK -> "b"
        }

    override val value: Int = 3




    companion object {
        val directions = listOf(
            -9,
            +9,
            -11,
            +11
        )
    }

}
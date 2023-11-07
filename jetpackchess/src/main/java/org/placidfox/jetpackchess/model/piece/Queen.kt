package org.placidfox.jetpackchess.model.piece

import org.placidfox.jetpackchess.R

class Queen(override val color: PlayerColor) : Piece {


    override val asset: Int =
        when (color) {
            PlayerColor.WHITE -> R.drawable.q_white
            PlayerColor.BLACK -> R.drawable.q_black
        }

    override val assetRatio: Float = 0.9f

    override val imgSymbol: String = when (color) {
        PlayerColor.WHITE -> "♕"
        PlayerColor.BLACK -> "♛"
    }

    override val textSymbol: String = "Q"

    override val FENSymbol: String =
        when(color) {
            PlayerColor.WHITE -> "Q"
            PlayerColor.BLACK -> "q"
        }

    override val value: Int = 9




    companion object {
        val directions = listOf(
            Bishop.directions + Rook.directions
        )
    }


}
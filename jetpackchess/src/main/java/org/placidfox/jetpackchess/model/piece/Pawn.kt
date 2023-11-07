package org.placidfox.jetpackchess.model.piece

import org.placidfox.jetpackchess.R

class Pawn(override val color: PlayerColor) : Piece {


    override val asset: Int =
        when (color) {
            PlayerColor.WHITE -> R.drawable.p_white
            PlayerColor.BLACK -> R.drawable.p_black
        }

    override val assetRatio: Float = 0.9f

    override val imgSymbol: String = when (color) {
        PlayerColor.WHITE -> "♙"
        PlayerColor.BLACK -> "♟︎"
    }

    override val textSymbol: String = ""

    override val FENSymbol: String =
        when(color) {
            PlayerColor.WHITE -> "P"
            PlayerColor.BLACK -> "p"
        }

    override val value: Int = 1




    companion object {
        val directions = listOf(
            +10,
            //+20, ( avoir comment gérer le premiers coup ?
            -9,
            +11
        )
    }


}
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

        val targetInitRank = listOf(
            +2
        )

        val target = listOf(
            +1,
        )

        val captureTargets = listOf(
            -9, +11
        )
    }


}
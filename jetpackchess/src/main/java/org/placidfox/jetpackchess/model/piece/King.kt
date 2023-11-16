package org.placidfox.jetpackchess.model.piece

import org.placidfox.jetpackchess.R

class King(override val color: PlayerColor) : Piece {

    override val asset: Int =
        when (color) {
            PlayerColor.WHITE -> R.drawable.k_white
            PlayerColor.BLACK -> R.drawable.k_black
        }

    override val assetRatio: Float = 0.9f

    override val imgSymbol: String = when (color) {
        PlayerColor.WHITE -> "♔"
        PlayerColor.BLACK -> "♚"
    }

    override val textSymbol: String = "K"

    override val FENSymbol: String =
        when(color) {
            PlayerColor.WHITE -> "K"
            PlayerColor.BLACK -> "k"
        }

    override val value: Int = 0 // Value utile pour Roi ?

    companion object {
        val targets = listOf(
            -11,
            +11,
            -10,
            +10,
            -9,
            +9,
            -1,
            +1
        )

        val shortCastleTargets = 20 // TO CHECK - FCT TO TEST IF CASTEL NOT POSSIBLE (CONTROLLED SQUARED INSIDE CASTEL MOVEMENT)

        val longCastleTargets = -20 // TO CHECK - FCT TO TEST IF CASTEL NOT POSSIBLE (CONTROLLED SQUARED INSIDE CASTEL MOVEMENT)
    }



}
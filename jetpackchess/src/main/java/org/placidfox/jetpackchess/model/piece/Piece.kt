package org.placidfox.jetpackchess.model.piece
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.model.game.GamePosition


interface Piece {

    val color: PlayerColor

    val asset: Int
    val assetRatio: Float

    val imgSymbol: String
    val textSymbol: String

    val FENSymbol: String

    val value: Int


}






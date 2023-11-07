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


    /** List of accessible squares without Illegal Moves (ifChecked or Pinned Pieces) **/
    fun accessibleSquares(gamePosition: GamePosition): List<Square> = emptyList()

}






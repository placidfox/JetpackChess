package org.placidfox.jetpackchess.model.move

import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.GamePosition
import org.placidfox.jetpackchess.model.game.parameters.CastleType
import org.placidfox.jetpackchess.model.piece.*

class ProposedMove(
    val from: Coordinate,
    val to: Coordinate,
    private val position: GamePosition,
    var promotionTo: Class<out Piece>? = null
) {

    private val pieceToMove: Piece = position.board.findPiece(from)!!

    private val isPawnToMove: Boolean =
        pieceToMove::class.java == Pawn::class.java


    val isPromotionMove: Boolean =
        when (position.activePlayer){
            PlayerColor.WHITE -> isPawnToMove && to.rank == 8
            PlayerColor.BLACK -> isPawnToMove && to.rank == 1
        }



}



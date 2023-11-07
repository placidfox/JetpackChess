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

    private val isKingToMove: Boolean =
        pieceToMove::class.java == King::class.java

    private val isPawnToMove: Boolean =
        pieceToMove::class.java == Pawn::class.java


    val isPromotionMove: Boolean =
        when (position.activePlayer){
            PlayerColor.WHITE -> isPawnToMove && to.rank == 8
            PlayerColor.BLACK -> isPawnToMove && to.rank == 1
        }


    val isCastleMove: Boolean =
        isKingToMove && mapCastleMoveUci.contains(Pair(from, to))

    val castleType: CastleType? =
        when (from to to) {
            Coordinate.E1 to Coordinate.G1 -> CastleType.WHITE_SHORT_CASTLE
            Coordinate.E1 to Coordinate.C1 -> CastleType.WHITE_LONG_CASTLE
            Coordinate.E8 to Coordinate.G8 -> CastleType.BLACK_SHORT_CASTLE
            Coordinate.E8 to Coordinate.C8 -> CastleType.BLACK_LONG_CASTLE
            else -> null
        }


    val isEnPassantMove: Boolean =
        isPawnToMove && position.enPassantStatus.enPassantCoordinate == to

    val isCaptureMove: Boolean =
        position.board.isOccupied(to)



}



val mapCastleMoveUci: List<Pair<Coordinate, Coordinate>>
    = listOf(
        Coordinate.E1 to Coordinate.G1,
        Coordinate.E1 to Coordinate.C1,
        Coordinate.E8 to Coordinate.G8,
        Coordinate.E8 to Coordinate.C8,
    )

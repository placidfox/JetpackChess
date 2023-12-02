package org.placidfox.jetpackchess.model.move

import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.GamePosition
import org.placidfox.jetpackchess.model.game.parameters.CastleType
import org.placidfox.jetpackchess.model.piece.*

class AppliedMove(
    val from: Coordinate,
    val to: Coordinate,
    private val position: GamePosition,
    val promotionTo: Class<out Piece>? = null
) {

    val pieceToMove: Piece = position.board.findPiece(from)!!
    val pieceToMoveClass: Class<out Piece> = pieceToMove::class.java
    val pieceToMoveColor: PlayerColor = pieceToMove.color

    val pieceCaptured: Piece?
        get() =
            if(isEnPassantCapture) {
                when(position.activePlayer){
                    PlayerColor.WHITE -> position.board.findPiece(Coordinate.fromNumCoordinate(to.file, to.rank - 1))
                    PlayerColor.BLACK -> position.board.findPiece(Coordinate.fromNumCoordinate(to.file, to.rank + 1))
                }
            } else {
                position.board.findPiece(to)
            }

    val pieceCapturedList
        get() = if (pieceCaptured !== null) {
            listOf(pieceCaptured!!)
        }
        else {
            emptyList()
        }

    // for enPassant
    val pieceRight: Piece? = Coordinate.getCoordinateRight(to)?.let { position.board.findPiece(it) }
    val pieceLeft:Piece? = Coordinate.getCoordinateLeft(to)?.let { position.board.findPiece(it) }

    val isPieceRightEnPassantPossible: Boolean
        get() = if (pieceRight != null) {
                pieceRight::class.java == Pawn::class.java && pieceRight.color == position.activePlayer.opponent()
            } else {
                false
          }


    val isPieceLeftEnPassantPossible: Boolean
        get() = if (pieceLeft != null) {
            pieceLeft::class.java == Pawn::class.java && pieceLeft.color == position.activePlayer.opponent()
        } else {
            false
        }

    val piecePromoteTo: Piece =
        when(promotionTo){
            Queen::class.java -> Queen(position.activePlayer)
            Bishop::class.java -> Bishop(position.activePlayer)
            Knight::class.java -> Knight(position.activePlayer)
            Rook::class.java -> Rook(position.activePlayer)
            else -> pieceToMove
        }


    private val isKingToMove: Boolean =
        pieceToMove::class.java == King::class.java

    val isCastleMove: Boolean =
        isKingToMove && mapCastleMoveUci.contains(Pair(from, to))

    val isCastle: CastleType? =
        if (isCastleMove) {

            when (from to to) {
                Coordinate.E1 to Coordinate.G1 -> CastleType.WHITE_SHORT_CASTLE
                Coordinate.E1 to Coordinate.C1 -> CastleType.WHITE_LONG_CASTLE
                Coordinate.E8 to Coordinate.G8 -> CastleType.BLACK_SHORT_CASTLE
                Coordinate.E8 to Coordinate.C8 -> CastleType.BLACK_LONG_CASTLE
                else -> null
            }
        } else {
            null
        }

    val isPawntoMove: Boolean =
        pieceToMove::class.java == Pawn::class.java

    val isEnableEnPassant: Boolean =
        if (isPawntoMove){
            when(from.rank to to.rank){
                2 to 4 ->
                    isPieceRightEnPassantPossible || isPieceLeftEnPassantPossible

                7 to 5 ->
                    isPieceRightEnPassantPossible || isPieceLeftEnPassantPossible

                else -> {false}
            }

        } else {
            false
        }

    val squareEnPassantAvailable: Coordinate?
        get() = if(isEnableEnPassant){
            when(position.activePlayer){
                PlayerColor.WHITE -> Coordinate.fromNumCoordinate(to.file, to.rank - 1)
                PlayerColor.BLACK -> Coordinate.fromNumCoordinate(to.file, to.rank + 1)
            }
        } else {
            null
        }

    val isEnPassantCapture: Boolean
        get() = (position.enPassantStatus.enPassantCoordinate == to) && (pieceToMoveClass == Pawn::class.java)


    val isPromotionMove
        get() = isPawntoMove && (to.rank == 1 || to.rank == 8)




}

val mapCastleMoveUci: List<Pair<Coordinate, Coordinate>>
        = listOf(
    Coordinate.E1 to Coordinate.G1,
    Coordinate.E1 to Coordinate.C1,
    Coordinate.E8 to Coordinate.G8,
    Coordinate.E8 to Coordinate.C8,
)



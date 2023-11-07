package org.placidfox.jetpackchess.viewModel

import org.placidfox.jetpackchess.model.board.Board
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.GamePosition
import org.placidfox.jetpackchess.model.game.parameters.CastleType
import org.placidfox.jetpackchess.model.game.parameters.CastlingStatus
import org.placidfox.jetpackchess.model.game.parameters.EnPassantStatus
import org.placidfox.jetpackchess.model.move.AppliedMove
import org.placidfox.jetpackchess.model.piece.*


fun UIViewModel.askPromotion(){
    showPromotionDialog.value = true
}

fun UIViewModel.promotionChoice(chosenPiece: Class<out Piece>){
    proposedMove!!.promotionTo = chosenPiece
    validateMove(proposedMove!!)
    switchPromotionDialog()
}


fun addMoveMadeGamePosition(gamePosition: GamePosition, moveUCI: AppliedMove) {
    gamePosition.nextMove = moveUCI
}
fun calculateNewPosition(gamePosition: GamePosition, moveUCI: AppliedMove): GamePosition {

    val newMap: MutableMap<Coordinate, Piece> = gamePosition.board.piecesPosition.toMutableMap()

    newMap.keys.remove(moveUCI.from)

    newMap[moveUCI.to] = moveUCI.pieceToMove // permet de garder la ref de la même pièce et remplacer la pièce prise

    // Deplacement de la pièce + Promotion (Auto en Dame pour le moment)
    when (moveUCI.isPromotionMove) {
        true -> newMap[moveUCI.to] = moveUCI.piecePromoteTo // doublon dans la fonction mais fonctionne ?
        else -> newMap[moveUCI.to] =
            moveUCI.pieceToMove // permet de garder la ref de la même pièce et remplacer la pièce prise

    }

    //make en Passant capture
    if (moveUCI.isEnPassantCapture) {
        when (gamePosition.activePlayer) {
            PlayerColor.WHITE -> newMap.keys.remove(
                Coordinate.fromNumCoordinate(
                    moveUCI.to.file,
                    moveUCI.to.rank - 1
                )
            )

            PlayerColor.BLACK -> newMap.keys.remove(
                Coordinate.fromNumCoordinate(
                    moveUCI.to.file,
                    moveUCI.to.rank + 1
                )
            )
        }
    }

    when (moveUCI.isCastle) {
        CastleType.WHITE_SHORT_CASTLE -> {
            newMap[Coordinate.F1] = newMap.getValue(Coordinate.H1)
            newMap.remove(Coordinate.H1)
        }

        CastleType.WHITE_LONG_CASTLE -> {
            newMap[Coordinate.D1] = newMap.getValue(Coordinate.A1)
            newMap.remove(Coordinate.A1)
        }

        CastleType.BLACK_SHORT_CASTLE -> {
            newMap[Coordinate.F8] = newMap.getValue(Coordinate.H8)
            newMap.remove(Coordinate.H8)
        }

        CastleType.BLACK_LONG_CASTLE -> {
            newMap[Coordinate.D8] = newMap.getValue(Coordinate.A8)
            newMap.remove(Coordinate.A8)
        }

        null -> Unit
    }


    // update EnPassantStatus
    val enPassantStatus = if (moveUCI.isEnableEnPassant) {
        EnPassantStatus(true, moveUCI.squareEnPassantAvailable)
    } else {
        EnPassantStatus(false, null)
    }


    //Fct calculatenewCastleStatus
    var newCastlingStatus: CastlingStatus = gamePosition.castlingStatus
    when (moveUCI.pieceToMoveClass to moveUCI.pieceToMoveColor) {
        King::class.java to PlayerColor.WHITE -> newCastlingStatus.whiteKingAsMoved()
        King::class.java to PlayerColor.BLACK -> newCastlingStatus.blackKingAsMoved()

        Rook::class.java to PlayerColor.WHITE ->
            when (moveUCI.from) {
                Coordinate.H1 -> newCastlingStatus.whiteRookH1asMoves()
                Coordinate.A1 -> newCastlingStatus.whiteRookA1asMoves()
                else -> {}
            }

        Rook::class.java to PlayerColor.BLACK ->
            when (moveUCI.from) {
                Coordinate.H8 -> newCastlingStatus.blackRookH8asMoves()
                Coordinate.A8 -> newCastlingStatus.blackRookA8asMoves()
                else -> {}
            }
    }

    addMoveMadeGamePosition(gamePosition, moveUCI)

    return GamePosition(
        board = Board(newMap),
        activePlayer = gamePosition.activePlayer.opponent(),
        halfMoves = if (moveUCI.pieceToMove::class.java != Pawn::class.java) {
            gamePosition.halfMoves + 1
        } else {
            gamePosition.halfMoves
        },
        nbMove = if (gamePosition.activePlayer == PlayerColor.WHITE) {
            gamePosition.nbMove
        } else {
            gamePosition.nbMove + 1
        },
        castlingStatus = newCastlingStatus,
        enPassantStatus = enPassantStatus,
        lastMove = moveUCI,
        nextMove = null,
        capturedPieces = gamePosition.capturedPieces + moveUCI.pieceCapturedList
    )
}
package org.placidfox.jetpackchess.viewModel

import org.placidfox.jetpackchess.controller.JetpackChessMode
import org.placidfox.jetpackchess.model.board.Board
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.GamePosition
import org.placidfox.jetpackchess.model.game.parameters.CastleType
import org.placidfox.jetpackchess.model.game.parameters.EnPassantStatus
import org.placidfox.jetpackchess.model.move.AppliedMove
import org.placidfox.jetpackchess.model.move.ProposedMove
import org.placidfox.jetpackchess.model.piece.*


fun GameViewModel.updateButtonState(){

    isActivePositionFirst = activePositionIndex > 0
    isFirstPosition = activePositionIndex != 0

    when(mode){
        JetpackChessMode.GAME, JetpackChessMode.SCROLL -> {
            isActivePositionLast = activePositionIndex < gameTimeline.positionsTimeline.lastIndex
            isLastPosition = (activePositionIndex != gameTimeline.positionsTimeline.lastIndex)
        }
        JetpackChessMode.PUZZLE -> {
            isActivePositionLast = activePositionIndex < maxSeenPosition
            isLastPosition = maxSeenPosition == gameTimeline.positionsTimeline.lastIndex && activePositionIndex < gameTimeline.positionsTimeline.lastIndex
        }

    }

}

fun GameViewModel.initStatus(){
    when(mode){
        JetpackChessMode.GAME -> status.value = STATUS.IN_PROGRESS_GAME
        JetpackChessMode.PUZZLE  -> status.value = STATUS.IN_PROGRESS_OK
        JetpackChessMode.SCROLL -> status.value = STATUS.SCROLLING
    }
}

fun GameViewModel.statusMistake(){
    when(mode){
        JetpackChessMode.PUZZLE -> status.value = STATUS.IN_PROGRESS_WRONG
        else -> {} // Nothing to do in Game & Scroll Mode

    }
}

fun GameViewModel.checkEndStatus(){

    when(mode){
        JetpackChessMode.GAME -> {
            if (isKingCheckmate){
                status.value = STATUS.FINISH_CHECKMATE
            }

            if (isKingStalemate){
                status.value = STATUS.FINISH_STALEMATE
            }
        } //TODO() CHECK IF CHECKMATE OR STALEMATE OR GIVE UP
        JetpackChessMode.PUZZLE -> if(activePositionIndex == gameTimeline.positionsTimeline.lastIndex){
            if (status.value == STATUS.IN_PROGRESS_OK){
                status.value = STATUS.FINISH_OK
            } else {
                status.value = STATUS.FINISH_WRONG
            }
        }
        JetpackChessMode.SCROLL -> {} // Nothing to do in Scrolling Mode
    }

}

fun GameViewModel.setWrongMoveDecorator(proposedMove: ProposedMove){
    wrongMovePosition.value = listOf(proposedMove.from, proposedMove.to)
}

fun GameViewModel.resetWrongMoveDecorator(){
    wrongMovePosition.value = null
}


fun GameViewModel.askPromotion(){
    showPromotionDialog.value = true
}

fun GameViewModel.cancelPromotion(){
    showPromotionDialog.value = false
    resetSelectedSquare()
}

fun GameViewModel.promotionChoice(chosenPiece: Class<out Piece>){
    proposedMove!!.promotionTo = chosenPiece
    validateMove(proposedMove!!)
    switchPromotionDialog()
}


fun calculateNewPosition(gamePosition: GamePosition, moveUCI: AppliedMove): GamePosition {

    val newMap: MutableMap<Coordinate, Piece> = gamePosition.board.piecesPosition.toMutableMap()

    newMap.keys.remove(moveUCI.from)

    //Move the piece & change it if promotion
    when (moveUCI.isPromotionMove) {
        true -> newMap[moveUCI.to] = moveUCI.piecePromoteTo
        else -> newMap[moveUCI.to] =
            moveUCI.pieceToMove // keep same piece ref when moved

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
    val newCastlingStatus = gamePosition.castlingStatus.copy()

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
        capturedPieces = gamePosition.capturedPieces + moveUCI.pieceCapturedList,
    )
}
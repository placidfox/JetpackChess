package org.placidfox.jetpackchess.model.game

import android.text.BoringLayout
import androidx.compose.ui.res.booleanResource
import org.placidfox.jetpackchess.model.board.Board
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.parameters.CastlingStatus
import org.placidfox.jetpackchess.model.game.parameters.EnPassantStatus
import org.placidfox.jetpackchess.model.move.AppliedMove
import org.placidfox.jetpackchess.model.piece.Piece
import org.placidfox.jetpackchess.model.piece.PlayerColor
import org.placidfox.jetpackchess.viewModel.calculateNewPosition


data class GamePosition(
    val board: Board,
    val activePlayer: PlayerColor,

    val castlingStatus: CastlingStatus,
    val enPassantStatus: EnPassantStatus,

    val halfMoves: Int,
    val nbMove: Int,

    var lastMove: AppliedMove? = null,
    var nextMove: AppliedMove? = null,

    val capturedPieces: List<Piece?> = emptyList(),

    ){

        val lastMovePositions: List<Coordinate>? = lastMove?.let { listOf(it.from, it.to) }

        val activePlayerScore: Int
            get() = calculateScore(activePlayer)

        fun calculateScore(playerColor: PlayerColor): Int =
            board.piecesColorPosition(playerColor).values.sumOf {it.value} - board.piecesColorPosition(playerColor.opponent()).values.sumOf {it.value}

        val isCheckmate
            get() = getIsCheckmate()

        val isStalemate
            get() = getIsStalemate()

        var isActiveKingCheck =
            isKingCheck(activePlayer)

        fun isKingCheck(color: PlayerColor) : Boolean{

            var calculateIsKingChecked = false
            val kingCoordinate = board.kingPosition(color)

            board.piecesColorPosition(color.opponent()).forEach {
                entry -> if(entry.value.reachableSquares(this).contains(kingCoordinate)) {
                    calculateIsKingChecked = true
                }
            }

            return calculateIsKingChecked
        }

        fun pieceLegalDestinations(coordinate: Coordinate): List<Coordinate> =
            board.findPiece(coordinate)!!.reachableSquares(this)
                .applyPinnedConstraints(this, coordinate)


        private fun List<Coordinate>.applyPinnedConstraints(actualPosition: GamePosition, coordinate: Coordinate) : List<Coordinate> =
            filter {
                !calculateNewPosition(
                    actualPosition,
                    AppliedMove(coordinate, it, actualPosition)
                ).isKingCheck(activePlayer) // because turn has been made
            }


        private fun getIsCheckmate() = // Not calculate everytime for performance & pinned validation (sometime the King is Captures and don't exist) TODO FIX BUG
            !isLegalMoves() && isActiveKingCheck


        private fun getIsStalemate() = // Not calculate everytime for performance & pinned validation  (sometime the King is Captures and don't exist) TODO FIX BUG
            !isLegalMoves() && !isActiveKingCheck




        fun isLegalMoves(): Boolean { // TODO SIMPLIFY ?
            var isEmpty = false
            board.piecesColorPosition(activePlayer).forEach {
                if (pieceLegalDestinations(it.key).isNotEmpty()){
                    isEmpty = true
                }
            }
            return isEmpty
        }


}

enum class Termination {
    IN_PROGRESS,
    CHECKMATE,
    STALEMATE
}

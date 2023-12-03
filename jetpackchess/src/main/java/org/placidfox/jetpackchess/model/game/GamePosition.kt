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
    var activePlayer: PlayerColor,

    val castlingStatus: CastlingStatus,
    val enPassantStatus: EnPassantStatus,

    val halfMoves: Int,
    val nbMove: Int,

    var lastMove: AppliedMove? = null,
    var nextMove: AppliedMove? = null,

    val capturedPieces: List<Piece> = emptyList(),

    ){

        val lastMovePositions: List<Coordinate>? = lastMove?.let { listOf(it.from, it.to) }

        val activePlayerScore: Int
            get () = calculateScore(activePlayer)

        fun calculateScore(playerColor: PlayerColor): Int =
            board.piecesColorPosition(playerColor).values.sumOf {it.value} - board.piecesColorPosition(playerColor.opponent()).values.sumOf {it.value}


        var isActiveKingCheck =
            isKingCheck(activePlayer)

        var termination = Termination.IN_PROGRESS

        fun getActivePiecesPosition(): Set<Coordinate>{
            return getPiecesPosition(activePlayer)
        }

        fun getPiecesPosition(color: PlayerColor): Set<Coordinate>{
            return board.piecesColorPosition(color).keys
        }

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

        fun calculateTermination(){ // TODO TO SIMPIFY

            if (!isLegalMoves()){
                termination = if(isActiveKingCheck){
                    Termination.CHECKMATE
                } else {
                    Termination.STALEMATE
                }
            }
        }


        fun isLegalMoves(): Boolean {
            board.piecesColorPosition(activePlayer).forEach {
                  if (pieceLegalDestinations(it.key).isNotEmpty()) {
                       return true
                  }
            }
            return false
        }


}

enum class Termination {
    IN_PROGRESS,
    CHECKMATE,
    STALEMATE
}
